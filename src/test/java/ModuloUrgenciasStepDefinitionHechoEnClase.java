import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import mock.DBPruebaEnMemoriaClase;
import org.example.app.ServicioUrgencias;
import org.example.domain.Enfermera;
import org.example.domain.Ingreso;
import org.example.domain.NivelEmergencia;
import org.example.domain.Paciente;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ModuloUrgenciasStepDefinitionHechoEnClase {

    private Enfermera enfermera;
    private DBPruebaEnMemoriaClase dbPruebaEnMemoria;
    private ServicioUrgencias servicioUrgencias;

    public ModuloUrgenciasStepDefinitionHechoEnClase() {
        this.dbPruebaEnMemoria = new DBPruebaEnMemoriaClase();
        this.servicioUrgencias = new ServicioUrgencias(dbPruebaEnMemoria);
    }

    @Given("Que la siguiente enfermera esta registrada:")
    public void queLaSiguienteEnfermeraEstaRegistrada(List<Map<String, String>> tabla) {
        String nombre = tabla.getFirst().get("nombre");
        String apellido = tabla.getFirst().get("apellido");

        enfermera = new Enfermera(nombre, apellido);
    }

    @Given("que estan registrados los siguientes pacientes:")
    public void queEstanRegistradosLosSiguientesPacientes(List<Map<String, String>> tabla) {
        for (Map<String, String> fila : tabla) {
            String cuil = fila.get("Cuil");
            String nombre = fila.get("Nombre");
            String apellido = fila.get("Apellido");
            String obraSocial = fila.get("Obra Social");

            Paciente paciente = new Paciente(cuil, apellido, nombre, obraSocial);

            dbPruebaEnMemoria.guardarPaciente(paciente);

        }
    }

    @When("ingresa a urgencias el siguiente paciente:")
    public void ingresaAUrgenciasElSiguientePaciente(List<Map<String, String>> tabla) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        Map<String, String> fila = tabla.getFirst();

        String cuil = fila.get("Cuil");

        String informe = fila.get("Informe");

        NivelEmergencia nivelEmergencia =
                Arrays.stream(NivelEmergencia.values())
                        .filter(
                                nivel -> nivel.tieneNombre(fila.get("Nivel de Emergencia"))
                        )
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Nombre de nivel desconocido")
                        );

        Float temperatura = Float.parseFloat(fila.get("Temperatura"));

        Float frecuenciaCardiaca = Float.parseFloat(fila.get("Frecuencia Cardiaca"));

        Float frecuenciaRespiratoria = Float.parseFloat(fila.get("Frecuencia Respiratoria"));

        List<Float> tensionArterial =
                Arrays.stream(fila.get("Tension Arterial")
                        .split("/"))
                        .map(Float::parseFloat)
                        .toList();

        servicioUrgencias.registrarUrgencia(
                cuil,
                enfermera,
                informe,
                nivelEmergencia,
                temperatura,
                frecuenciaCardiaca,
                frecuenciaRespiratoria,
                tensionArterial.get(0),
                tensionArterial.get(1)
        );
    }

    @Then("la lista de espera de pacientes se ordena por cuil de la siguiente manera:")
    public void laListaDeEsperaDePacientesSeOrdenaPorCuilDeLaSiguienteManera(List<String> lista) {
        String cuilEsperado =lista.getFirst();

        List<String> cuilsPendientes =
                servicioUrgencias
                        .obtenerIngresosPendientes()
                        .stream()
                        .map(Ingreso::getCuilPaciente)
                        .toList();

        assertThat(cuilsPendientes)
                .hasSize(1)
                .contains(cuilEsperado);
    }
}
