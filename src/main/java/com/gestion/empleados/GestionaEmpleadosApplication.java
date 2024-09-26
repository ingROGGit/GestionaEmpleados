package com.gestion.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestionaEmpleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionaEmpleadosApplication.class, args);
	}

}
