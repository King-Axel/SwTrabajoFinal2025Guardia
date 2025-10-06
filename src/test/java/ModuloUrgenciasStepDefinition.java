import io.cucumber.java.PendingException;
import io.cucumber.java.es.*;
import mock.DBPruebaEnMemoria;
import org.example.app.ServicioUrgencias;
import org.example.domain.Enfermera;
import org.example.domain.Ingreso;
import org.example.domain.NivelEmergencia;
import org.example.domain.Paciente;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ModuloUrgenciasStepDefinition {
    private Enfermera enfermera;
    private DBPruebaEnMemoria dbPruebaEnMemoria;
    private ServicioUrgencias servicioUrgencias;
    private Exception ultimaExcepcion;

    public ModuloUrgenciasStepDefinition() {
        dbPruebaEnMemoria = new DBPruebaEnMemoria();
        servicioUrgencias = new ServicioUrgencias(dbPruebaEnMemoria);
    }

    @Dado("que la siguiente enfermera esta registrada y autenticada:")
    public void queLaSiguienteEnfermeraEstaRegistradaYAutenticada(Map<String, String> dataTable) {
        String apellido = dataTable.get("apellido");
        String nombre = dataTable.get("nombre");

        enfermera = new Enfermera(apellido, nombre);
    }

    @Y("los siguientes pacientes estan registrados:")
    public void losSiguientesPacientesEstanRegistrados(List<Map<String, String>> dataTable) {
        for (Map<String, String> fila : dataTable) {
            String cuil = fila.get("Cuil");
            String apellido = fila.get("Apellido");
            String nombre = fila.get("Nombre");

            Paciente paciente = new Paciente(cuil, apellido, nombre);

            dbPruebaEnMemoria.guardarPaciente(cuil, paciente);
        }
    }

    @Cuando("llega el paciente:")
    public void llegaElPaciente(List<Map<String, String>> dataTable) {
        Map<String, String> fila = dataTable.getFirst();

        String cuil = fila.get("Cuil");
        String apellido = fila.get("Apellido");
        String nombre = fila.get("Nombre");
        String informe = fila.get("Informe");
        String nivelEmergencia = fila.get("Nivel de Emergencia");
        String temperatura = fila.get("Temperatura");
        String frecuenciaCardiaca = fila.get("Frecuencia Cardiaca");
        String frecuenciaRespiratoria = fila.get("Frecuencia Respiratoria");
        String frecuenciaSistolica = fila.get("Frecuencia Sistolica");
        String frecuenciaDiastolica = fila.get("Frecuencia Diastolica");

        try {
            servicioUrgencias.registrarIngreso(
                    cuil,
                    apellido,
                    nombre,
                    enfermera,
                    informe,
                    nivelEmergencia,
                    temperatura,
                    frecuenciaCardiaca,
                    frecuenciaRespiratoria,
                    frecuenciaSistolica,
                    frecuenciaDiastolica
            );
        } catch(IllegalArgumentException e) {
            ultimaExcepcion = e;
        }
    }

    @Entonces("la cola de espera con cuils, ordenada por criticidad y hora de llegada, se ordena de la siguiente manera:")
    public void laColaDeEsperaConCuilsOrdenadaPorCriticidadYHoraDeLlegadaSeOrdenaDeLaSiguienteManera(List<String> cuilsEsperando) {
        List<String> cuilsPendientes =
                servicioUrgencias
                        .getListaEspera()
                        .stream()
                        .map(Ingreso::getCuilPaciente)
                        .toList();

        assertThat(cuilsPendientes)
                .hasSize(cuilsEsperando.size())
                .isEqualTo(cuilsEsperando);
    }

    @Entonces("el paciente {string} estara registrado:")
    public void elPacienteEstaraRegistrado(String cuil) {
        Paciente paciente = dbPruebaEnMemoria.obtenerPaciente(cuil)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        assertThat(paciente).isNotEqualTo(null);
    }

    @Entonces("se emite el siguiente mensaje:")
    public void seEmiteElSiguienteMensaje(List<String> dataTable) {
        assertThat(ultimaExcepcion.getMessage()).isNotNull().isEqualTo(dataTable.getFirst());
    }

    @Cuando("llegan los pacientes:")
    public void lleganLosPacientes(List<Map<String, String>> dataTable) {
        for (Map<String, String> fila : dataTable) {
            String cuil = fila.get("Cuil");
            String apellido = fila.get("Apellido");
            String nombre = fila.get("Nombre");
            String informe = fila.get("Informe");
            String nivelEmergencia = fila.get("Nivel de Emergencia");
            String temperatura = fila.get("Temperatura");
            String frecuenciaCardiaca = fila.get("Frecuencia Cardiaca");
            String frecuenciaRespiratoria = fila.get("Frecuencia Respiratoria");
            String frecuenciaSistolica = fila.get("Frecuencia Sistolica");
            String frecuenciaDiastolica = fila.get("Frecuencia Diastolica");

            try {
                servicioUrgencias.registrarIngreso(
                        cuil,
                        apellido,
                        nombre,
                        enfermera,
                        informe,
                        nivelEmergencia,
                        temperatura,
                        frecuenciaCardiaca,
                        frecuenciaRespiratoria,
                        frecuenciaSistolica,
                        frecuenciaDiastolica
                );
            } catch(IllegalArgumentException e) {
                ultimaExcepcion = e;
            }

        }
    }
}