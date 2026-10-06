package com.gestion.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class GestionaEmpleadosApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GestionaEmpleadosApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GestionaEmpleadosApplication.class, args);
	}
	// Agrega este Bean para forzar el timeout en servidores externos
	@Bean
	public ServletContextInitializer sessionTimeoutInitializer() {
		return servletContext -> {
			// El valor se asigna estrictamente en MINUTOS (8 horas = 480 minutos)
			servletContext.setSessionTimeout(480); 
		};
	}
}
