package mock;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Paciente;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBPruebaEnMemoriaClase implements RepositorioPacientes {
    private Map<String, Paciente> listaPacientes;

    public DBPruebaEnMemoriaClase() {
        this.listaPacientes = new HashMap<>();
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        listaPacientes.put(paciente.getCuil(), paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientePorCuil(String cuil) {
        return Optional.ofNullable(listaPacientes.get(cuil));
    }
}
