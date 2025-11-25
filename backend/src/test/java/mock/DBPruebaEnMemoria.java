package mock;

import com.grupocinco.app.interfaces.RepositorioPacientes;
import com.grupocinco.domain.Paciente;

import java.util.*;

public class DBPruebaEnMemoria implements RepositorioPacientes {
    private final Map<String, Paciente> pacientes;
    /*private final Map<String, Ingreso> enProceso = new HashMap<>();

    public void registrarEnProceso(Ingreso ingreso) {
        enProceso.put(ingreso.getCuilPaciente(), ingreso);
    }

    public Optional<Ingreso> obtenerEnProcesoPorCuil(String cuil) {
        return Optional.ofNullable(enProceso.get(cuil));
    }*/

    public DBPruebaEnMemoria() {
        pacientes = new HashMap<>();
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        pacientes.put(paciente.getCuil(), paciente);
    }

    @Override
    public Optional<Paciente> obtenerPaciente(String cuil) {
        return Optional.ofNullable(pacientes.get(cuil));
    }

    @Override
    public void registrarPaciente(Paciente paciente) {
        pacientes.put(paciente.getCuil(), paciente);
    }
}
