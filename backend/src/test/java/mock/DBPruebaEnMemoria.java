package mock;

import com.grupocinco.app.interfaces.IRepositorioPacientes;
import com.grupocinco.domain.Paciente;

import java.util.*;

public class DBPruebaEnMemoria implements IRepositorioPacientes {
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
    public void save(Paciente paciente) {
        pacientes.put(paciente.getCuil(), paciente);
    }

    @Override
    public Optional<Paciente> findByCuil(String cuil) {
        return Optional.ofNullable(pacientes.get(cuil));
    }

    @Override
    public List<Paciente> findAll() {
        return List.of();
    }
}
