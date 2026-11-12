package com.gestion.empleados.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            // Funciona en versiones anteriores de Tomcat
            connector.setMaxParameterCount(10000);
            // Configura maxPartCount dinámicamente si el conector interno lo soporta
            connector.setProperty("maxPartCount", "100");
        });
    }
}