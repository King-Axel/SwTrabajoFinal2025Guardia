package com.grupocinco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// El exclude es para evitar definir una DB ya, sin eso hace falta definir una DB para que el Backend pueda correr
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SwTrabajoFinal2025GuardiaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwTrabajoFinal2025GuardiaApplication.class, args);
    }
}
