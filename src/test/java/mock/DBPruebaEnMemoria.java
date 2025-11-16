package mock;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Afiliado;
import org.example.domain.Domicilio;
import org.example.domain.ObraSocial;
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

    @Override
    public Paciente ingresarPaciente(String cuil, String apellido, String nombre, Domicilio domicilio, Afiliado afiliado) {
        //Ver si el paciente existe o no en la db
        Paciente paciente = new Paciente(cuil, apellido, nombre, domicilio, afiliado);
        pacientes.put(cuil, paciente);
        return paciente;
    }
}
