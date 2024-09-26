package com.gestion.empleados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
//@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	PasswordEncoder passwordEcoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector)
			throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector).servletPath("/path");
		return httpSecurity.authorizeHttpRequests(auth -> {
			auth.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll();
			auth.requestMatchers(mvcMatcherBuilder.pattern("/form/*")).hasRole("ADMIN");
			auth.requestMatchers(mvcMatcherBuilder.pattern("/eliminar/*")).hasRole("ADMIN");
			auth.anyRequest().authenticated();
		}).formLogin(login -> {
			login.loginPage("/login").permitAll();
			login.successHandler(successHandler());
		}).logout(logout -> logout.permitAll()).sessionManagement(session -> {
			session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
			session.invalidSessionUrl("/login");
			session.maximumSessions(1);
			session.sessionFixation().migrateSession();
		}).build();
	}

	public AuthenticationSuccessHandler successHandler() {
		return ((request, response, authentication) -> {
			response.sendRedirect("/menu");
		});
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
