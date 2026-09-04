package com.gestion.empleados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestionSINAVIDController {
	@GetMapping({ "/GestionSINAVID/ListaSinSINAVID"})
	public String contacto(Model model) {
		model.addAttribute("titulo", "En Construccion");
		return "GestionSINAVID/ListaSinSINAVID";
	}
}
