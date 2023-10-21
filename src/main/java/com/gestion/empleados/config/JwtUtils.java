package com.gestion.empleados.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtils {
	private static String SECRET_KEY = "P4$$w0rdJWT";
	private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

	public String create(String usuerName) {
		return JWT.create().withSubject(usuerName).withIssuer("SYSADMIN").withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() * TimeUnit.DAYS.toMillis(1))).sign(ALGORITHM);
	}

	public boolean isValid(String jwt) {
		try {
			JWT.require(ALGORITHM).build().verify(jwt);
			return true;
		} catch (JWTVerificationException err) {
			return false;
		}
	}
	public String getUserName(String jwt) {
		return JWT.require(ALGORITHM).build().verify(jwt).getSubject();
	}
}
