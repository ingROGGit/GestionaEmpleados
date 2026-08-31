package com.gestion.empleados.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.ServiciosEntity;
import com.gestion.empleados.entity.filtrosConsultaDTO;
import com.gestion.empleados.repository.PuestosRepositoryJPA;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;
import com.gestion.empleados.utils.PageRender;

@Controller
public class NominaController {
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private QuincenaRepositoryJPA quincenaJPA;
	@Autowired
	private PuestosRepositoryJPA puestosJPA;
	@Autowired
	private ServiciosRepositoryJPA serviciosJPA;
	@GetMapping("quincenas/listarQuincena")
	public String listarQNA(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "7000") int size,
			@RequestParam(defaultValue = "empleadoQN.id,asc") String[] sort) {
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = quincenasCatJPA.findQNAACT(fechaSqlHoy);
		QuincenaCatEntity quinShearch=quincenasCatJPA.findByIdQNA(quincenasCat);
		filtrosConsultaDTO filtros = new filtrosConsultaDTO();
		List<String> LquincenasCat = quincenasCatJPA.findByAllIdQNA();
		List<String> lpuesto = puestosJPA.findByAllPuesto();
		List<String> lservicio = serviciosJPA.findByAllServicio();
		String sortField = sort[0];
		String sortDirection = sort[1];
		Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Order order = new Order(direction, sortField);
		Pageable pageRequest = PageRequest.of(page - 1, size, Sort.by(order));
		Page<QuincenasEntity> quincena;
		Long idEmpleado=(long) 0;
		if(keyword==null)
			quincena= quincenaJPA.findByQuinCat(quinShearch,pageRequest);
		else {
			try {
				idEmpleado=Long.valueOf(keyword);
			}catch(Exception err) {}
			quincena= quincenaJPA.findByQuinCatAndEmpleadoQN_IdOrEmpleadoQN_NombreCompletoContainingIgnoreCaseOrEmpleadoQN_CurpContainingIgnoreCaseOrEmpleadoQN_RfcContainingIgnoreCase(quinShearch,idEmpleado,keyword,keyword,keyword,pageRequest);
		}
		PageRender<QuincenasEntity> pageRender = new PageRender<>("/quincenas/listarQuincena", quincena);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("QUINCENA" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		filtros.setQna(quincenasCat);
		model.addAttribute("filtros", filtros);
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado QUINCENA");
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("lpuesto", lpuesto);
		model.addAttribute("lservicio", lservicio);
		model.addAttribute("quincena", quincena);
		model.addAttribute("page", pageRender);
		model.addAttribute("pageSize", size);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("currentPage", quincena.getNumber() + 1);
		model.addAttribute("totalItems", quincena.getTotalElements());
		model.addAttribute("totalPages", quincena.getTotalPages());
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		model.addAttribute("funLista", "quincenas/listarQuincena");
		model.addAttribute("funVer", "verXMLRec");
		model.addAttribute("funStatus", "statusXMLRec");
		return "quincenas/listarQuincena";
	}
	@PostMapping("/quincenas/listarQuincena")
	public String listarQNA(Model model, filtrosConsultaDTO filtrosSet,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "empleadoQN.id,asc") String[] sort) {
		List<PuestosEntity> lpuesto = puestosJPA.findAll();
		List<ServiciosEntity> lservicio = serviciosJPA.findAll();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("QUINCENA" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		String sortField = sort[0];
		String sortDirection = sort[1];
		Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Order order = new Order(direction, sortField);
		Pageable pageRequest = PageRequest.of(page - 1, size, Sort.by(order));
		Page<QuincenasEntity> quincena = null;
		quincena = quincenaJPA.findAll(pageRequest);
		List<QuincenaCatEntity> LquincenasCat = quincenasCatJPA.findAll();
		PageRender<QuincenasEntity> pageRender = new PageRender<>("/cfdi/listaXMLRec", quincena);
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("lpuesto", lpuesto);
		model.addAttribute("lservicio", lservicio);
		model.addAttribute("titulo", "Listado QUINCENA");
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("quincena", quincena);
		model.addAttribute("page", pageRender);
		model.addAttribute("filtros", filtrosSet);
		model.addAttribute("page", pageRender);
		model.addAttribute("pageSize", size);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("currentPage", quincena.getNumber() + 1);
		model.addAttribute("totalItems", quincena.getTotalElements());
		model.addAttribute("totalPages", quincena.getTotalPages());
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		model.addAttribute("funLista", "listaXMLEmi");
		model.addAttribute("funVer", "verXMLRec");
		model.addAttribute("funStatus", "statusXMLRec");
		return "quincenas/listarQuincena";
	}
}
