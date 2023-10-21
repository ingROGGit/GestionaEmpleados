package com.gestion.empleados.config;

//import static org.springframework.security.config.Customizer.withDefaults;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.gestion.empleados.service.EmpleadoService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
//@EnableWebSecurity
public class WebSecurityConfig {
//	private final JwtFilter jwtFilter;
//	@Autowired
//	public WebSecurityConfig(JwtFilter jwtFilter) {
//		this.jwtFilter = jwtFilter;
//		
//	}
    @Bean
    PasswordEncoder passwordEcoder() {
		return new BCryptPasswordEncoder();
	}
 
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails usuario1 = User.withUsername("UsuConsulta").password("").roles("USER").build();
//		UserDetails usuario2 = User.withUsername("UsuAdmin").password("").roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(usuario1, usuario2);
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
//	}
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector).servletPath("/path"); 
///// Basica
    	http
    	.csrf().disable()
        .authorizeHttpRequests((authorize) -> authorize 
        		.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
        		.requestMatchers(mvcMatcherBuilder.pattern("/form/*")).hasRole("ADMIN")
        		.requestMatchers(mvcMatcherBuilder.pattern("/eliminar/*")).hasRole("ADMIN")
    			.anyRequest().authenticated()
    		)
        .formLogin().loginPage("/login").permitAll()
        .and().logout().permitAll()
        ;
    	
    	
//////////////// funcional    	
//	    	http
//	        .csrf()
//	        .and()
////	        .cors(withDefaults())
//	        .authorizeHttpRequests((authorize) -> authorize 
//	        		.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
//	        		.requestMatchers(mvcMatcherBuilder.pattern("/form/*")).hasRole("ADMIN")
//	        		.requestMatchers(mvcMatcherBuilder.pattern("/eliminar/*")).hasRole("ADMIN")
//	    			.anyRequest().authenticated()
//	    		)
//	        .formLogin().loginPage("/login").permitAll()
//	        .and().logout().permitAll()
//	        ;
	    	
	    	
///////////////// 	// Restricciones para GET microservices
//	    	http
//	        .csrf().disable()
//	        .cors()
//	        .and()
//	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        .and()
//	        .authorizeHttpRequests((authorize) -> authorize 
//	        		.requestMatchers(mvcMatcherBuilder.pattern("/login/aut")).permitAll()
//	        		.requestMatchers(mvcMatcherBuilder.pattern("/reports/**")).hasAuthority("random_reports")
//	        		 .requestMatchers(mvcMatcherBuilder.pattern("/admin")).hasRole("ADMIN")
//	                 .requestMatchers(mvcMatcherBuilder.pattern("/user")).hasRole("USER")
//	                 .requestMatchers(mvcMatcherBuilder.pattern("/public")).permitAll()
//	    			.anyRequest().authenticated()
//	    		)
//	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//	    	//O
////	        .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
	return http.build();
	}
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
    	return config.getAuthenticationManager();
    }
//    @Bean
//    UserDetailsService memoryUsers() {
//		UserDetails admin= User.builder()
//				.username("usuAdmin")
//				.password(passwordEcoder().encode("123Tamarindo"))
//				.roles("ADMIN")
//				.build();
//		UserDetails usuConsulta= User.builder()
//				.username("usuCon")
//				.password(passwordEcoder().encode("123"))
//				.roles("CUSTOMER")
//				.build();
//		return new InMemoryUserDetailsManager(admin,usuConsulta);
//	}
}
