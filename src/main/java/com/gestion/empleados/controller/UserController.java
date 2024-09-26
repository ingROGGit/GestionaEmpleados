package com.gestion.empleados.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.gestion.empleados.utils.PasswordGenerator;

import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/usuarios/listarUsuarios")
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
//		Pageable pageRequest = PageRequest.of(page, 3);
//		Page<Empleados> empleados = empleadoService.findAll(pageRequest);
//		PageRender<Empleados> pageRender = new PageRender<>("/listar", empleados);
//		model.addAttribute("titulo", "Listado Empleados");
//		model.addAttribute("empleados", empleados);
//		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista de Usuarios");
		return "/usuarios/listarUsuarios";
	}
//
//	@GetMapping("/ver/{id}")
//	public String verDetallesEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
//			RedirectAttributes flash) {
//		Empleados empleado = empleadoService.findOne(id);
//		if (empleado == null) {
//			flash.addFlashAttribute("error", "El empleado no Existe");
//			return "redirect:/listar";
//		}
//		modelo.put("empleado", empleado);
//		modelo.put("titulo", "Detalles del Empleado" + empleado.getNombre());
//		return "ver";
//	}
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

	@PostMapping("/usuarios/formUsu")
	public String guardaEmpleado(CreateUserDTO createUserDTO, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		PasswordGenerator psg= new PasswordGenerator();
		Set<RoleEntity> roles= createUserDTO.getRoles().stream().map(role -> RoleEntity.builder()
				.name(ERole.valueOf(role))
				.build())
				.collect(Collectors.toSet());
		UsuariosEntity usuariosEntity = UsuariosEntity.builder()
				.username(createUserDTO.getUsu())
				.pass(psg.getPassword(createUserDTO.getPass()) )
				.bloqueado(createUserDTO.getBloqueado())
				.disabled(createUserDTO.getDisabled())
				.roles(roles).build();
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Empleado");
			return "formUsu";
		}
		String mensaje = "Empleado Registrado con Exito";
		userRepository.save(usuariosEntity);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listarUsuarios";
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
