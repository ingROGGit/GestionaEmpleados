package com.gestion.empleados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CuentasBancariasController {
	@GetMapping({ "/cuentasBancarias/ListaEmpSinCuenta"})
	public String contacto(Model model) {
		model.addAttribute("titulo", "En Construccion");
		return "cuentasBancarias/ListaEmpSinCuenta";
	}
}
