package com.gestion.empleados.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.Dto.LoginDto;
import com.gestion.empleados.config.JwtUtils;

@RestController
@RequestMapping
public class JWTController {
	private final AuthenticationManager auteAuthenticationManager;
	private final JwtUtils jwutil;
	  private static final Logger log = LoggerFactory.getLogger(JWTController.class);

	@Autowired
	public JWTController(AuthenticationManager authenticationManager, JwtUtils jwutil) {
		this.auteAuthenticationManager=authenticationManager;
		this.jwutil = jwutil;
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<Void> login(@RequestBody LoginDto logindto){
//		UsernamePasswordAuthenticationToken login= new UsernamePasswordAuthenticationToken(logindto.getUsuerName(), logindto.getPassword());
//		Authentication authen=this.auteAuthenticationManager.authenticate(login);
//		String jwt=this.jwutil.create(logindto.getUsuerName());
//		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
//	}
    @PostMapping("/login/aut")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
    	log.info("ini Tocken");
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.auteAuthenticationManager.authenticate(login);

        log.info(Boolean.toString(authentication.isAuthenticated()));
        log.info(authentication.getPrincipal().toString());

        String jwt = this.jwutil.create(loginDto.getUsername());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }
}
