package mock;

import io.cucumber.java.es.*;
import org.example.app.ServicioUrgencias;
import org.example.domain.*;
import org.example.app.interfaces.RepositorioPacientes;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

public class ModuloReclamoPacientesStepDefinition {

    private final RepositorioPacientes db;   // inyecta tu implementación en el constructor si usas DI
    private final ServicioUrgencias servicio;

    private Enfermera enfermera;
    private Medico medico;
    private Ingreso ingresoReclamado;
    private Exception ultimaExcepcion;

    public ModuloReclamoPacientesStepDefinition() {
        // Si en tus tests usas la DB en memoria de pruebas, reemplaza por la tuya:
        this.db = new mock.DBPruebaEnMemoria();
        this.servicio = new ServicioUrgencias(db);
    }

    // --------- Antecedentes (textos exclusivos del feature de Reclamo) ---------

    @Dado("que la siguiente enfermera esta registrada y autenticada en el sistema:")
    public void queLaSiguienteEnfermeraEstaRegistradaYAutenticadaEnElSistema(Map<String,String> row){
        enfermera = new Enfermera(row.get("Apellido"), row.get("Nombre"));
    }

    @Y("los siguientes pacientes estan registrados en el sistema:")
    public void losSiguientesPacientesEstanRegistradosEnElSistema(List<Map<String,String>> table){
        for (Map<String,String> r : table){
            Paciente p = new Paciente(r.get("Cuil"), r.get("Apellido"), r.get("Nombre"));
            db.obtenerORegistrarPaciente(p.getCuil(), p.getApellido(), p.getNombre());
        }
    }

    // --------- Carga de ingresos (texto exclusivo) ---------

    @Y("llegan los siguientes pacientes:")
    public void lleganLosSiguientesPacientes(List<Map<String, String>> dataTable){
        for (Map<String, String> fila : dataTable) {
            try {
                servicio.registrarIngreso(
                        fila.get("Cuil"),
                        fila.get("Apellido"),
                        fila.get("Nombre"),
                        enfermera,
                        fila.get("Informe"),
                        fila.get("Nivel de Emergencia"),
                        fila.get("Temperatura"),
                        fila.get("Frecuencia Cardiaca"),
                        fila.get("Frecuencia Respiratoria"),
                        fila.get("Frecuencia Sistolica"),
                        fila.get("Frecuencia Diastolica")
                );
            } catch (IllegalArgumentException e) {
                ultimaExcepcion = e;
            }
        }
    }

    // --------- Autenticación de médico (exclusivo) ---------

    @Y("el siguiente médico está autenticado:")
    public void elSiguienteMedicoEstaAutenticado(Map<String,String> row){
        medico = new Medico(row.get("Apellido"), row.get("Nombre"));
    }

    // --------- Acción principal ---------

    @Cuando("el médico reclama el próximo paciente de la lista de espera")
    public void elMedicoReclamaElProximoPacienteDeLaListaDeEspera(){
        try {
            ingresoReclamado = servicio.reclamarProximoPaciente(medico);
        } catch (IllegalStateException e){
            ultimaExcepcion = e;
        }
    }

    // --------- Asserts ---------

    @Entonces("el paciente reclamado es:")
    public void elPacienteReclamadoEs(List<Map<String, String>> table) {
        String cuilEsperado = table.get(0).get("Cuil"); // usa el header
        assertThat(ingresoReclamado).isNotNull();
        assertThat(ingresoReclamado.getCuilPaciente()).isEqualTo(cuilEsperado);
    }


    @Entonces("el estado del ingreso de {string} es {string}")
    public void elEstadoDelIngresoEs(String cuil, String estado){
        var ing = servicio.obtenerIngresoEnProcesoPorCuil(cuil).orElseThrow();
        assertThat(ing.getEstado().name()).isEqualTo(estado);
        assertThat(ing.getMedico()).isEqualTo(medico);
    }

    @Entonces("Y la cola de espera queda de la siguiente manera:")
    public void laColaDeEsperaQuedaDeLaSiguienteManera(List<String> cuilsEsperando){
        var cuilsPendientes = servicio.getListaEspera()
                .stream()
                .map(Ingreso::getCuilPaciente)
                .toList();
        assertThat(cuilsPendientes).isEqualTo(cuilsEsperando);
    }


    // ⬇⬇⬇ TEXTO EXCLUSIVO DE RECLAMO
    @Entonces("se emite el siguiente mensaje de reclamo:")
    public void seEmiteElSiguienteMensajeDeReclamo(List<String> mensaje){
        assertThat(ultimaExcepcion).isNotNull();
        assertThat(ultimaExcepcion.getMessage()).isEqualTo(mensaje.getFirst());
    }
}
