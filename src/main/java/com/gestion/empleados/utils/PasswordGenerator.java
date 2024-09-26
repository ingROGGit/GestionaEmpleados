package com.gestion.empleados.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public String getPassword(String pass) {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		return encoder.encode(pass);
	}
}
