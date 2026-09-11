package com.gestion.empleados.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.empleados.entity.filtrosConsultaDTO;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;

@Controller
public class EstadisticasController {
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private QuincenaRepositoryJPA quincenaJPA;
	@GetMapping("/estadisticas/chartsTipoPagos")
	public String listarEmpleados(Model model) throws JsonProcessingException{
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = this.quincenasCatJPA.findQNAACT(fechaSqlHoy);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
        // 1. Datos simulados (pueden venir de un Repository/Base de datos)
        List<String> navegadores = Arrays.asList("CHEQUE", "RECIBO", "SIN PAGO");
        List<Long> visitas = Arrays.asList(this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Cheque",quincenasCat), this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Recibo",quincenasCat),this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("TEMPORAL",quincenasCat));

        // 2. Convertir las listas de Java a formato JSON (String)
        ObjectMapper mapper = new ObjectMapper();
        String jsonLabels = mapper.writeValueAsString(navegadores);
        String jsonValues = mapper.writeValueAsString(visitas);

        // 3. Pasar los JSON al modelo de Thymeleaf
        model.addAttribute("graficaLabels", jsonLabels);
        model.addAttribute("graficaValues", jsonValues);
        model.addAttribute("LquincenasCat", LquincenasCat);
        model.addAttribute("quinCatSelectPost", quincenasCat);
        model.addAttribute("funLista", "estadisticas/chartsTipoPagos");
		model.addAttribute("titulo","SI");
		return "/estadisticas/chartsTipoPagos";
	}
	@PostMapping("/estadisticas/chartsTipoPagos")
	public String listarEmpleadosPost(Model model,@RequestParam("quinCatSelectPost") String quinCatSelect) throws JsonProcessingException{
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
        // 1. Datos simulados (pueden venir de un Repository/Base de datos)
        List<String> navegadores = Arrays.asList("CHEQUE", "RECIBO", "SIN PAGO");
        List<Long> visitas = Arrays.asList(this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Cheque",quinCatSelect), this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Recibo",quinCatSelect),this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("TEMPORAL",quinCatSelect));

        // 2. Convertir las listas de Java a formato JSON (String)
        ObjectMapper mapper = new ObjectMapper();
        String jsonLabels = mapper.writeValueAsString(navegadores);
        String jsonValues = mapper.writeValueAsString(visitas);

        // 3. Pasar los JSON al modelo de Thymeleaf
        model.addAttribute("graficaLabels", jsonLabels);
        model.addAttribute("graficaValues", jsonValues);
        model.addAttribute("LquincenasCat", LquincenasCat);
        model.addAttribute("quinCatSelectPost", quinCatSelect);
        model.addAttribute("funLista", "estadisticas/chartsTipoPagos");
		model.addAttribute("titulo","SI");
		return "/estadisticas/chartsTipoPagos";
	}
}
