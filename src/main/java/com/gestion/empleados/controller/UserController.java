package com.gestion.empleados.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.DTO.CreateUserDTO;
import com.gestion.empleados.entity.ERole;
import com.gestion.empleados.entity.RoleEntity;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.RoleRepository;
import com.gestion.empleados.repository.UserRepository;
import com.gestion.empleados.repository.UsuariosEntityRepositoryJPA;
import com.gestion.empleados.service.UsuariosEntityService;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.PasswordGenerator;


@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UsuariosEntityService usuariosEntityService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UsuariosEntityRepositoryJPA usuJPA;
	
	@GetMapping("/usuarios/listarUsuarios")
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		String  ususRestrict= "usuAdmin";
//		String  ususRestrict2= "usuConsulta";
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<UsuariosEntity> usuarios;
		if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains("ROLE_ADMIN"))
			usuarios = usuariosEntityService.findAll(pageRequest);
		else usuarios = usuJPA.findByUsernameNotLike(pageRequest,ususRestrict);
		PageRender<UsuariosEntity> pageRender = new PageRender<>("/usuarios/listarUsuarios", usuarios);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista de Usuarios");
		return "/usuarios/listarUsuarios";
	}

	@GetMapping("/usuarios/ver/{id}")
	public String verDetallesUsuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		UsuariosEntity usuario = usuariosEntityService.findOne(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El empleado no Existe");
			return "redirect:/listar";
		}
		modelo.put("usuario", usuario);
		modelo.put("titulo", "Detalles del Usuario: " + usuario.getUsername());
		return "usuarios/verUsu";
	}
//	@GetMapping({ "/", "/menu", "" })
//	public String menuUsu(Model model) {
//		
//		return "menu";
//	}
	@GetMapping("/usuarios/formUsu")
	public String formularioRegistroEmpleado(Model model) {
		CreateUserDTO usu = new CreateUserDTO();
		Iterable<RoleEntity> roles= roleRepository.findAll();
		model.addAttribute("roles", roles);
		model.addAttribute("usu", usu);
		model.addAttribute("titulo", "Registro de Empleado");
		return "/usuarios/formUsu";
	}

	@PostMapping("/usuarios/formUsuN")
	public String guardaUsuario(CreateUserDTO createUserDTO, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		String mensaje = null;
		createUserDTO.setBloqueado(false);
		createUserDTO.setDisabled(false);
		PasswordGenerator psg= new PasswordGenerator();
		createUserDTO.getRoles().remove("");
		Set<RoleEntity> roles= new HashSet<>();
		Iterable<RoleEntity> Iroles= roleRepository.findAll();
		for(RoleEntity rol:Iroles) {
			for(String r:createUserDTO.getRoles()) {
				if(rol.getName().compareTo(ERole.valueOf(r))==0) {
					roles.add(rol);
				}
					
			}
		}
		UsuariosEntity usuariosEntity = UsuariosEntity.builder()
				.username(createUserDTO.getUsu())
				.pass(psg.getPassword(createUserDTO.getPass()) )
				.bloqueado(createUserDTO.getBloqueado())
				.disabled(createUserDTO.getDisabled())
				.roles(roles).build();
		if(createUserDTO.getId()!=null) {
			usuariosEntity.setId(createUserDTO.getId());
			mensaje="Usuario Actualizado con Exito";
		}else mensaje="Usuario Registrado con Exito";
		userRepository.save(usuariosEntity);
		status.setComplete();
		CreateUserDTO usu = new CreateUserDTO();
		modelo.addAttribute("roles", Iroles);
		modelo.addAttribute("usu", usu);
		modelo.addAttribute("success", mensaje);
		return "usuarios/formUsu";
	}

	@GetMapping("/usuarios/formUsu/{id}")
	public String editarUsuario(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		UsuariosEntity usuariosEntity = null;
		CreateUserDTO createUserDTO= null;
		if (id > 0) {
			usuariosEntity = usuariosEntityService.findOne(id);
			createUserDTO= new CreateUserDTO();
			createUserDTO.setId(usuariosEntity.getId());
			createUserDTO.setUsu(usuariosEntity.getUsername());
			createUserDTO.setBloqueado(usuariosEntity.getBloqueado());
			createUserDTO.setDisabled(usuariosEntity.getDisabled());
			Set<String> addRoles= new HashSet<>();
			for(RoleEntity rol:usuariosEntity.getRoles()) {
				addRoles.add(rol.getName().toString()+",");
			}
			createUserDTO.setRoles(addRoles);
			Iterable<RoleEntity> roles= roleRepository.findAll();
			modelo.put("roles", roles);
			modelo.put("usu", createUserDTO);
			modelo.put("titulo", "Edicion de Usuario");
		}
		return "usuarios/formUsu";
	}

	@GetMapping("/usuarios/eliminar/{id}")
	public String eliminarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			usuariosEntityService.delete(id);
			flash.addFlashAttribute("success", "Empleado Eliminado con Exito");
		}
		return "redirect:/usuarios/listarUsuarios";
	}
	@GetMapping("/usuarios/bloquear/{id}")
	public String bloquearUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			UsuariosEntity usuariosEntity = usuariosEntityService.findOne(id);
			usuariosEntity.setBloqueado(true);
			userRepository.save(usuariosEntity);
			flash.addFlashAttribute("success", "Empleado Bloqueado con Exito");
		}
		return "redirect:/usuarios/listarUsuarios";
	}
	@GetMapping("/usuarios/desactivar/{id}")
	public String desactivarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			UsuariosEntity usuariosEntity = usuariosEntityService.findOne(id);
			usuariosEntity.setDisabled(true);
			userRepository.save(usuariosEntity);
			flash.addFlashAttribute("success", "Empleado Desactivado con Exito");
		}
		return "redirect:/usuarios/listarUsuarios";
	}

}
