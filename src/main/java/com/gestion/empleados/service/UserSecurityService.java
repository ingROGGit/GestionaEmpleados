package com.gestion.empleados.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.empleados.JPA.UserRole;
import com.gestion.empleados.JPA.UserRolePK;
import com.gestion.empleados.JPA.Usuarios;
import com.gestion.empleados.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService{

	private final UserRepository userRepository;
	@Autowired
	public UserSecurityService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuarios> usuario=this.userRepository.findById(username);
		if(usuario.isEmpty())
			new UsernameNotFoundException("user:"+username+" not Found");
		String[] roles=(String[]) usuario.get().getUserRoleList().stream().map(UserRole::getRole).toArray(String[] :: new);
		return User.builder()
				.username(usuario.get().getUsu())
				.password(usuario.get().getPass())
//				.roles(roles)
				.authorities(this.grantedAuthority(roles))
				.accountLocked(usuario.get().getBloqueado())
				.accountExpired(usuario.get().getDisabled())
				.build();
	}
	private String[] getAuthorities(String rol) {
		if("ADMIN".equals(rol)||"CONSULTA".equals(rol)) {
			return new String[] {"random_reports"};
		}
		return new String[] {};
	}
	private List<GrantedAuthority> grantedAuthority(String[] roles){
		List<GrantedAuthority> lgat= new ArrayList<>(roles.length);
		for(String rol:roles) {
			lgat.add(new SimpleGrantedAuthority("ROLE_"+rol));
			for(String autority: this.getAuthorities(rol)) {
				lgat.add(new SimpleGrantedAuthority(autority));
			}
		}
		return lgat;
	}

}
