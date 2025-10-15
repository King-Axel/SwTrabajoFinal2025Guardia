import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.example.domain.Ingreso;
import org.example.domain.Medico;

import java.util.List;
import java.util.Map;

public class ModuloAtencionStepdefs {
    private Medico medico;

    @Given("que la siguiente medica esta autenticada y registrada")
    public void queLaSiguienteMedicaEstaAutenticadaYRegistrada(List<Map<String, String>> tabla) {
        String matricula = tabla.getFirst().get("matricula");
        medico = new Medico(matricula);
        throw new PendingException();
    }

    @When("la doctora registra el informe del paciente")
    public void laDoctoraRegistraElInformeDelPaciente() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("la doctora registra el informe del paciente sin el informe")
    public void laDoctoraRegistraElInformeDelPacienteSinElInforme() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
