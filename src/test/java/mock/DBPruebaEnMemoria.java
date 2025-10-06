package mock;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Paciente;

import java.util.*;

public class DBPruebaEnMemoria implements RepositorioPacientes {
    private Map<String, Paciente> pacientes;

    public DBPruebaEnMemoria() {
        pacientes = new HashMap<>();
    }

    @Override
    public void guardarPaciente(String cuil, Paciente paciente) {
        pacientes.put(cuil, paciente);
    }

    @Override
    public Optional<Paciente> obtenerPaciente(String cuil) {
        return Optional.ofNullable(pacientes.get(cuil));
    }

    @Override
    public Paciente obtenerORegistrarPaciente(String cuil, String apellido, String nombre) {
        return pacientes.computeIfAbsent(cuil,
                key -> new Paciente(key, apellido, nombre));
    }

    @Override
    public Paciente registrarPaciente(String cuil, Paciente paciente) {
        pacientes.put(cuil, paciente);

        return paciente;
    }
}
