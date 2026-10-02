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
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.filtrosConsultaDTO;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.SINAVIDRepositoryJPA;

@Controller
public class EstadisticasController {
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private QuincenaRepositoryJPA quincenaJPA;
	@Autowired
	private SINAVIDRepositoryJPA sinavidJPA;
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;

	@GetMapping("/estadisticas/chartsTipoPagos")
	public String chartsTipoPagos(Model model) throws JsonProcessingException {
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = this.quincenasCatJPA.findQNAACT(fechaSqlHoy);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		// 1. Datos simulados (pueden venir de un Repository/Base de datos)
		List<String> navegadores = Arrays.asList("CHEQUE", "RECIBO", "SIN PAGO");
		List<Long> visitas = Arrays.asList(this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Cheque", quincenasCat),
				this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Recibo", quincenasCat),
				this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("TEMPORAL", quincenasCat));

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
		model.addAttribute("titulo", "ESTADISTICAS DE PAGOS QUINCENA " + quincenasCat);
		return "/estadisticas/chartsTipoPagos";
	}

	@PostMapping("/estadisticas/chartsTipoPagos")
	public String chartsTipoPagosPost(Model model, @RequestParam("quinCatSelectPost") String quinCatSelect)
			throws JsonProcessingException {
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		// 1. Datos simulados (pueden venir de un Repository/Base de datos)
		List<String> navegadores = Arrays.asList("CHEQUE", "RECIBO", "SIN PAGO");
		List<Long> visitas = Arrays.asList(this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Cheque", quinCatSelect),
				this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("Recibo", quinCatSelect),
				this.quincenaJPA.countByTipoPagoAndQuinCat_IdQNA("TEMPORAL", quinCatSelect));

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
		model.addAttribute("titulo", "ESTADISTICAS DE PAGOS QUINCENA " + quinCatSelect);
		return "/estadisticas/chartsTipoPagos";
	}

	@GetMapping("/estadisticas/chartsSINAVID")
	public String chartsSINAVID(Model model) throws JsonProcessingException {
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = this.quincenasCatJPA.findQNAACT(fechaSqlHoy);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		QuincenaCatEntity quinCat = this.quincenasCatJPA.findByIdQNA(quincenasCat);

		// 1. Datos simulados (pueden venir de un Repository/Base de datos)
		List<String> navegadores = Arrays.asList("VALIDACION", "ALTA", "FALTA ALTA", "YA ALTA");

		List<Long> visitas = Arrays.asList(this.sinavidJPA.countByEstatus("VALIDACION"),
				this.sinavidJPA.countByFechaAltaBeforeAndEstatus(quinCat.getFechaFin(), "ALTA"),
				this.sinavidJPA.countByFechaAltaBeforeAndEstatusIsNull(quinCat.getFechaFin())
						+ this.empleadosJPA.countBySinavidIsNull(),
				this.sinavidJPA.countByFechaAltaBeforeAndEstatus(quinCat.getFechaFin(), "YA ALTA"));

		// 2. Convertir las listas de Java a formato JSON (String)
		ObjectMapper mapper = new ObjectMapper();
		String jsonLabels = mapper.writeValueAsString(navegadores);
		String jsonValues = mapper.writeValueAsString(visitas);

		// 3. Pasar los JSON al modelo de Thymeleaf
		model.addAttribute("graficaLabels", jsonLabels);
		model.addAttribute("graficaValues", jsonValues);
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("quinCatSelectPost", quincenasCat);
		model.addAttribute("funLista", "estadisticas/chartsSINAVID");
		model.addAttribute("titulo", "ESTADISTICAS SINAVID QUINCENA " + quincenasCat);
		return "/estadisticas/chartsSINAVID";
	}

	@PostMapping("/estadisticas/chartsSINAVID")
	public String chartsSINAVIDPost(Model model, @RequestParam("quinCatSelectPost") String quinCatSelect)
			throws JsonProcessingException {
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		QuincenaCatEntity quinCat = this.quincenasCatJPA.findByIdQNA(quinCatSelect);
		// 1. Datos simulados (pueden venir de un Repository/Base de datos)
		List<String> navegadores = Arrays.asList("VALIDACION", "ALTA", "FALTA ALTA", "YA ALTA");

		List<Long> visitas = Arrays.asList(this.sinavidJPA.countByEstatus("VALIDACION"),
				this.sinavidJPA.countByFechaAltaBeforeAndEstatus(quinCat.getFechaFin(), "ALTA"),
				this.sinavidJPA.countByFechaAltaBeforeAndEstatusIsNull(quinCat.getFechaFin())
						+ this.empleadosJPA.countBySinavidIsNull(),
				this.sinavidJPA.countByFechaAltaBeforeAndEstatus(quinCat.getFechaFin(), "YA ALTA"));

		// 2. Convertir las listas de Java a formato JSON (String)
		ObjectMapper mapper = new ObjectMapper();
		String jsonLabels = mapper.writeValueAsString(navegadores);
		String jsonValues = mapper.writeValueAsString(visitas);

		// 3. Pasar los JSON al modelo de Thymeleaf
		model.addAttribute("graficaLabels", jsonLabels);
		model.addAttribute("graficaValues", jsonValues);
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("quinCatSelectPost", quinCatSelect);
		model.addAttribute("funLista", "estadisticas/chartsSINAVID");
		model.addAttribute("titulo", "ESTADISTICAS SINAVID QUINCENA " + quinCatSelect);
		return "/estadisticas/chartsSINAVID";
	}

	@GetMapping("/estadisticas/chartsEmpleados")
	public String chartschartsEmpleados(Model model) throws JsonProcessingException {

		// 1. Datos simulados (pueden venir de un Repository/Base de datos)
		List<String> navegadores = Arrays.asList("RESIDENTES", "EVENTUAL", "BAJAS", "BASE");

		List<Long> visitas = Arrays.asList(this.empleadosJPA.countByTipoContrato("RESIDENTES"),
				this.empleadosJPA.countByTipoContrato("EVENTUAL"), this.empleadosJPA.countByActivo(false),
				this.empleadosJPA.countByTipoContrato("BASE"));

		// 2. Convertir las listas de Java a formato JSON (String)
		ObjectMapper mapper = new ObjectMapper();
		String jsonLabels = mapper.writeValueAsString(navegadores);
		String jsonValues = mapper.writeValueAsString(visitas);

		// 3. Pasar los JSON al modelo de Thymeleaf
		model.addAttribute("graficaLabels", jsonLabels);
		model.addAttribute("graficaValues", jsonValues);
		model.addAttribute("funLista", "estadisticas/chartsEmpleados");
		model.addAttribute("titulo", "ESTADISTICAS DE EMPLEADOS POR TIPO DE CONTRATO ");
		return "/estadisticas/chartsEmpleados";
	}
//	@PostMapping("/estadisticas/chartsEmpleados")
//	public String chartschartsEmpleadosPost(Model model,@RequestParam("quinCatSelectPost") String quinCatSelect) throws JsonProcessingException{
//        // 1. Datos simulados (pueden venir de un Repository/Base de datos)
//        List<String> navegadores = Arrays.asList( "NUEVOS", "EVENTUAL","BAJAS","BASE");
//        
//        List<Long> visitas = Arrays.asList(this.empleadosJPA.countByTipoContrato("NUEVOS"),
//        		this.empleadosJPA.countByTipoContrato("EVENTUAL"),
//        		this.empleadosJPA.countByTipoContrato("BAJAS"),
//        		this.empleadosJPA.countByTipoContrato("BASE"));
//
//        // 2. Convertir las listas de Java a formato JSON (String)
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonLabels = mapper.writeValueAsString(navegadores);
//        String jsonValues = mapper.writeValueAsString(visitas);
//
//        // 3. Pasar los JSON al modelo de Thymeleaf
//        model.addAttribute("graficaLabels", jsonLabels);
//        model.addAttribute("graficaValues", jsonValues);
//        model.addAttribute("quinCatSelectPost", quinCatSelect);
//        model.addAttribute("funLista", "estadisticas/chartsEmpleados");
//		model.addAttribute("titulo","SI");
//		return "/estadisticas/chartsEmpleados";
//	}
}
