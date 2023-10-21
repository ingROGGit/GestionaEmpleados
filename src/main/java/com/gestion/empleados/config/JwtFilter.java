package com.gestion.empleados.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtils jwtutils;
	private final UserDetailsService userDetailsService;
	@Autowired
	public JwtFilter(JwtUtils jwtutils, UserDetailsService userDetailsService) {
		this.jwtutils = jwtutils;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String autHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (autHeader == null || autHeader.isEmpty() || autHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwt = autHeader.split(" ")[1].trim();
		if (!this.jwtutils.isValid(jwt)) {
			filterChain.doFilter(request, response);
			return;
		}
		String username=this.jwtutils.getUserName(jwt);
		User user= (User)this.userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authenticaTocken= new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		authenticaTocken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticaTocken);
		filterChain.doFilter(request, response);
	}

}
