package com.grupocinco.data;

import com.grupocinco.app.repository.*;
import com.grupocinco.app.util.Rol;
import com.grupocinco.domain.*;
import com.grupocinco.domain.valueobject.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final RepositorioCuentas repositorioCuentas;
    private final RepositorioObrasSociales repositorioObrasSociales;
    private final RepositorioPacientes repositorioPacientes;
    private final RepositorioPersonal repositorioPersonal;
    private final RepositorioIngresos repositorioIngresos;

    private final PasswordEncoder encoder;

    public DataLoader(
            RepositorioObrasSociales repositorioObrasSociales,
            RepositorioPersonal repositorioPersonal,
            RepositorioPacientes repositorioPacientes,
            RepositorioCuentas repositorioCuentas,
            RepositorioIngresos repositorioIngresos,
            PasswordEncoder encoder
    ) {
        this.repositorioObrasSociales = repositorioObrasSociales;
        this.repositorioPacientes = repositorioPacientes;
        this.repositorioPersonal = repositorioPersonal;
        this.repositorioCuentas = repositorioCuentas;
        this.repositorioIngresos = repositorioIngresos;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        cargarObrasSociales();
        cargarPacientes();
        cargarPersonal();
        cargarCuentas();
        cargarIngresos();
    }

    private void cargarObrasSociales() {
        ObraSocial osep     = new ObraSocial("OSEP Tucumán");
        ObraSocial pami     = new ObraSocial("PAMI");
        ObraSocial osde     = new ObraSocial("OSDE");
        ObraSocial swiss    = new ObraSocial("Swiss Medical");
        ObraSocial galeno   = new ObraSocial("Galeno");
        ObraSocial medife   = new ObraSocial("Medifé");
        ObraSocial siprosa  = new ObraSocial("SIPROSA Tucumán");
        ObraSocial sancor   = new ObraSocial("Sancor Salud");
        ObraSocial omint    = new ObraSocial("Omint");
        ObraSocial osecac   = new ObraSocial("OSECAC");

        repositorioObrasSociales.save(osep);
        repositorioObrasSociales.save(pami);
        repositorioObrasSociales.save(osde);
        repositorioObrasSociales.save(swiss);
        repositorioObrasSociales.save(galeno);
        repositorioObrasSociales.save(medife);
        repositorioObrasSociales.save(siprosa);
        repositorioObrasSociales.save(sancor);
        repositorioObrasSociales.save(omint);
        repositorioObrasSociales.save(osecac);
    }

    private void cargarPacientes() {
        ObraSocial osep     = repositorioObrasSociales.findByName("OSEP Tucumán").orElseThrow();
        ObraSocial pami     = repositorioObrasSociales.findByName("PAMI").orElseThrow();
        ObraSocial osde     = repositorioObrasSociales.findByName("OSDE").orElseThrow();
        ObraSocial galeno   = repositorioObrasSociales.findByName("Galeno").orElseThrow();
        ObraSocial medife   = repositorioObrasSociales.findByName("Medifé").orElseThrow();

        Paciente p1 = new Paciente(
                "Gómez",
                "María",
                "27-23456789-1",
                new Domicilio("Av. Mate de Luna", 2500, "San Miguel de Tucumán"),
                new Afiliado(osep, "OSEP-458721")
        );

        Paciente p2 = new Paciente(
                "Rojas",
                "Carlos",
                "20-34567890-1",
                new Domicilio("Calle San Martín", 130, "Yerba Buena"),
                new Afiliado(pami, "PAMI-3098754")
        );

        Paciente p3 = new Paciente(
                "Pérez",
                "Luciana",
                "27-45632189-0",
                new Domicilio("Av. Belgrano", 980, "San Miguel de Tucumán"),
                new Afiliado(osde, "OSDE-558742")
        );

        Paciente p4 = new Paciente(
                "Salvatierra",
                "Héctor",
                "23-12345678-9",
                new Domicilio("Rivadavia", 420, "Tafí Viejo"),
                new Afiliado(galeno, "GAL-998721")
        );

        Paciente p5 = new Paciente(
                "Villalba",
                "Rocío",
                "27-11122233-3",
                new Domicilio("Lamadrid", 850, "Banda del Río Salí"),
                new Afiliado(medife, "MED-784512")
        );

        repositorioPacientes.save(p1);
        repositorioPacientes.save(p2);
        repositorioPacientes.save(p3);
        repositorioPacientes.save(p4);
        repositorioPacientes.save(p5);
    }

    private void cargarPersonal() {
        repositorioPersonal.save(new Enfermera("Lopez","Jacinta Maria", "27-23589461-0"));
        repositorioPersonal.save(new Enfermera("Pérez","Ana Lucía", "27-24569741-0"));
        repositorioPersonal.save(new Medico("Gomez", "Carlos Alberto", "1212212121", "20-31223344-8"));
        repositorioPersonal.save(new Medico("Rivas", "Julia", "9898989898", "27-40991234-6"));
    }

    private void cargarCuentas() {
        repositorioCuentas.save(
                new Cuenta(Email.of("lopezjacinta@gmail.com"),
                        Contrasena.of(encoder.encode("contrasena")),
                        Rol.ENFERMERA,
                        repositorioPersonal.findByCuil("27-23589461-0").get()
                )
        );

        repositorioCuentas.save(
                new Cuenta(Email.of("gomezcarlos@gmail.com"),
                        Contrasena.of(encoder.encode("contrasena")),
                        Rol.MEDICO,
                        repositorioPersonal.findByCuil("27-40991234-6").get()
                )
        );
    }

    private void cargarIngresos() {
        Paciente p1 = repositorioPacientes.findByCuil("27-23456789-1").orElseThrow();
        Paciente p2 = repositorioPacientes.findByCuil("20-34567890-1").orElseThrow();
        Paciente p3 = repositorioPacientes.findByCuil("27-45632189-0").orElseThrow();
        Paciente p4 = repositorioPacientes.findByCuil("23-12345678-9").orElseThrow();

        Enfermera e1 = (Enfermera) repositorioPersonal.findByCuil("27-23589461-0").orElseThrow();
        Enfermera e2 = (Enfermera) repositorioPersonal.findByCuil("27-24569741-0").orElseThrow();

        Ingreso i1 = new Ingreso(
                p1,
                e1,
                "Fiebre alta y malestar general.",
                NivelEmergencia.EMERGENCIA,
                Temperatura.of("39.2"),
                FrecuenciaCardiaca.of("110"),
                FrecuenciaRespiratoria.of("25"),
                TensionArterial.of("130", "85")
        );

        Ingreso i2 = new Ingreso(
                p2,
                e2,
                "Dolor en pecho desde hace 20 minutos.",
                NivelEmergencia.CRITICA,
                Temperatura.of("36.8"),
                FrecuenciaCardiaca.of("130"),
                FrecuenciaRespiratoria.of("30"),
                TensionArterial.of("150", "95")
        );

        Ingreso i3 = new Ingreso(
                p3,
                e1,
                "Dificultad respiratoria leve.",
                NivelEmergencia.URGENCIA,
                Temperatura.of("37.0"),
                FrecuenciaCardiaca.of("95"),
                FrecuenciaRespiratoria.of("20"),
                TensionArterial.of("120", "80")
        );

        Ingreso i4 = new Ingreso(
                p4,
                e2,
                "Golpe en el brazo izquierdo, posible contusión.",
                NivelEmergencia.URGENCIA_MENOR,
                Temperatura.of("36.5"),
                FrecuenciaCardiaca.of("82"),
                FrecuenciaRespiratoria.of("16"),
                TensionArterial.of("118", "76")
        );

        repositorioIngresos.save(i1);
        repositorioIngresos.save(i2);
        repositorioIngresos.save(i3);
        repositorioIngresos.save(i4);
    }
}
