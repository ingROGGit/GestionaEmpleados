package com.gestion.empleados.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserSecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuariosEntity> usuario = this.userRepository.findByUsername(username);
		if (usuario.isEmpty())
			new UsernameNotFoundException("user:" + username + " not Found");
		Collection<? extends GrantedAuthority> authorities = usuario.get().getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
				.collect(Collectors.toSet());
		return User.builder().username(usuario.get().getUsername()).password(usuario.get().getPass())
				.authorities(authorities).accountLocked(usuario.get().getBloqueado())
				.accountExpired(usuario.get().getDisabled()).build();
	}
}
