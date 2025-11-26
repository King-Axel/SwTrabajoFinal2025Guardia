import com.grupocinco.app.ServicioUrgencias;
import com.grupocinco.app.exceptions.PacienteInexistenteException;
import com.grupocinco.domain.Enfermera;
import com.grupocinco.domain.Paciente;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import mock.DBPruebaEnMemoria;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ModuloUrgenciasStepDefinition {
    private final ServicioUrgencias servicio;
    private final DBPruebaEnMemoria db;
    private Exception ultimaExcepcion;
    private Enfermera enfermera;

    public ModuloUrgenciasStepDefinition() {
        db = new DBPruebaEnMemoria();
        servicio = new ServicioUrgencias(db);
    }

    @Dado("que la siguiente enfermera esta registrada y autenticada:")
    public void queLaSiguienteEnfermeraEstaRegistradaYAutenticada(List<Map<String, String>> datosEnfermera) {
        var datos = datosEnfermera.get(0);

        String apellido = datos.get("Apellido");
        String nombre = datos.get("Nombre");
        String cuil = datos.get("Cuil");

        enfermera = new Enfermera(apellido, nombre, cuil);
    }

    @Y("los siguientes pacientes estan registrados:")
    public void losSiguientesPacientesEstanRegistrados(List<Map<String, String>> datosPacientes) {
        for (var fila : datosPacientes) {
            String apellido = fila.get("Apellido");
            String nombre = fila.get("Nombre");
            String cuil = fila.get("Cuil");

            Paciente paciente = new Paciente(apellido, nombre, cuil);

            db.save(paciente);
        }
    }

    @Cuando("llega el paciente:")
    public void llegaElPaciente(List<Map<String, String>> datosPaciente) {
        registrarIngresos(datosPaciente);
    }

    @Cuando("llegan los pacientes:")
    public void lleganLosPacientes(List<Map<String, String>> datosPacientes) {
        registrarIngresos(datosPacientes);
    }

    private void registrarIngresos(List<Map<String, String>> datosPacientes) {
        for(var datos : datosPacientes) {
            String apellido = datos.get("Apellido");
            String nombre = datos.get("Nombre");
            String cuil = datos.get("Cuil");

            String informe = datos.get("Informe");
            String nivelEmergencia = datos.get("Nivel de Emergencia");
            String temperatura = datos.get("Temperatura");
            String frecuenciaCardiaca = datos.get("Frecuencia Cardiaca");
            String frecuenciaResp = datos.get("Frecuencia Respiratoria");
            String frecuenciaSistolica = datos.get("Frecuencia Sistolica");
            String frecuenciaDiastolica = datos.get("Frecuencia Diastolica");

            try {
                servicio.registrarIngreso(
                        cuil, apellido, nombre, enfermera, informe,
                        nivelEmergencia, temperatura, frecuenciaCardiaca,
                        frecuenciaResp, frecuenciaSistolica, frecuenciaDiastolica
                );
            } catch (Exception e) {
                ultimaExcepcion = e;
            }
        }
    }

    @Entonces("la cola de espera con cuils, ordenada por criticidad y hora de llegada, se ordena de la siguiente manera:")
    public void laColaDeEsperaConCuilsOrdenadaPorCriticidadYHoraDeLlegadaSeOrdenaDeLaSiguienteManera(List<String> colaEsperada) {
        List<String> colaReal = servicio.getListaEspera().stream().map(i -> i.getPaciente().getCuil()).toList();

        assertThat(colaReal).hasSize(colaEsperada.size()).isEqualTo(colaEsperada);
    }

    @Entonces("el paciente {string} estara registrado:")
    public void elPacienteEstaraRegistrado(String cuilPaciente) {
        Paciente paciente = db.findByCuil(cuilPaciente).orElseThrow(
                () -> new PacienteInexistenteException("El paciente con el cuil " + cuilPaciente + " no existe")
        );

        assertThat(paciente).isNotEqualTo(null);
    }

    @Entonces("se emite el siguiente mensaje:")
    public void seEmiteElSiguienteMensaje(List<String> mensaje) {
        assertThat(ultimaExcepcion).isNotNull();
        assertThat(ultimaExcepcion.getMessage()).isEqualTo(mensaje.get(0));
    }
}
