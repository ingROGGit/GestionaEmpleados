package com.gestion.empleados.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.DTO.CreateUserDTO;
import com.gestion.empleados.entity.ERole;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.RoleEntity;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.RoleRepository;
import com.gestion.empleados.repository.UserRepository;
import com.gestion.empleados.service.UsuariosEntityService;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.PasswordGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UsuariosEntityService usuariosEntityService;
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/usuarios/listarUsuarios")
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<UsuariosEntity> usuarios = usuariosEntityService.findAll(pageRequest);
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
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Usuario");
//			return "formUsu";
		}
		String mensaje = "Usuario Registrado con Exito";
		userRepository.save(usuariosEntity);
		status.setComplete();
		CreateUserDTO usu = new CreateUserDTO();
		modelo.addAttribute("roles", Iroles);
		modelo.addAttribute("usu", usu);
		modelo.addAttribute("success", mensaje);
		modelo.addAttribute("titulo", "Registro de Empleado");
		return "usuarios/formUsu";
	}

//	@GetMapping("/form/{id}")
//	public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
//			RedirectAttributes flash) {
//		Empleados empleado = null;
//		if (id > 0) {
//			empleado = empleadoService.findOne(id);
//			if (empleado == null) {
//				flash.addFlashAttribute("error", "El Empleado no Existe");
//				return "redirect:/listar";
//			}
//		} else {
//			flash.addFlashAttribute("error", "El Empleado no Existe");
//			return "redirect:/listar";
//		}
//		modelo.put("empleado", empleado);
//		modelo.put("titulo", "Edicion de Empleado");
//		return "form";
//	}
//
//	@GetMapping("/eliminar/{id}")
//	public String eliminarEmpleado(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
//		if (id > 0) {
//			empleadoService.delete(id);
//			flash.addFlashAttribute("success", "Empleado Eliminado con Exito");
//		}
//		return "redirect:/listar";
//	}

}
