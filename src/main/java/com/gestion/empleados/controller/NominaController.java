package com.gestion.empleados.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.ServiciosEntity;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.entity.filtrosConsultaDTO;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.PuestosRepositoryJPA;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;
import com.gestion.empleados.utils.PageRender;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

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
	@Autowired
	private EmpleadosRepositoryJPA empleadoRJPA;

	@GetMapping("quincenas/listarQuincena")
	public String listarQNA(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "7000") int size,
			@RequestParam(defaultValue = "empleadoQN.id,asc") String[] sort,
			@RequestParam(required = false) String quinCatSelectGet) {
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = quincenasCatJPA.findQNAACT(fechaSqlHoy);
		QuincenaCatEntity quinShearch;
		if (quinCatSelectGet != null)
			quinShearch = quincenasCatJPA.findByIdQNA(quinCatSelectGet);
		else {
			quinShearch = quincenasCatJPA.findByIdQNA(quincenasCat);
			quinCatSelectGet = quincenasCat;
		}
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
		Long idEmpleado = (long) 0;
		if (keyword == null || keyword.isEmpty())
			quincena = quincenaJPA.findByQuinCat(quinShearch, pageRequest);
		else {
			try {
				idEmpleado = Long.valueOf(keyword);
			} catch (Exception err) {
			}
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
//			quincena= quincenaJPA.findByQuinCatKeyword(quinShearch,keyword,idEmpleado,keyword,keyword,keyword,keyword,pageable);
			if (idEmpleado > 0) {
				quincena = quincenaJPA.findByQuinCatAndEmpleadoQN_Id(quinShearch, idEmpleado, pageable);
			} else {
				Collection<Empleados> collEmpleados = new HashSet<>();
				collEmpleados = empleadoRJPA
						.findByNombreCompletoContainingIgnoreCaseOrCurpContainingIgnoreCaseOrRfcContainingIgnoreCase(
								keyword, keyword, keyword);
				quincena = quincenaJPA.findByQuinCatAndEmpleadoQNIn(quinShearch, collEmpleados, pageable);
			}
		}
		PageRender<QuincenasEntity> pageRender = new PageRender<>("/quincenas/listarQuincena", quincena);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file = new File("QUINCENA" + fa + ".xlsx");
		boolean fileExDownload = false;
		if (file.exists() && file.canWrite()) {
			fileExDownload = true;
		}
		filtros.setQna(quincenasCat);
		model.addAttribute("filtros", filtros);
		model.addAttribute("fileExDownload", fileExDownload);
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
		model.addAttribute("quinCatSelectGet", quinCatSelectGet);
		model.addAttribute("registros",quincena.getTotalElements());
		return "quincenas/listarQuincena";
	}

	@PostMapping("/quincenas/listarQuincena")
	public String listarQNA(Model model, filtrosConsultaDTO filtrosSet, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5000") int size,
			@RequestParam(defaultValue = "empleadoQN.id,asc") String[] sort) {
		List<String> LquincenasCat = quincenasCatJPA.findByAllIdQNA();
		List<String> lpuesto = puestosJPA.findByAllPuesto();
		List<String> lservicio = serviciosJPA.findByAllServicio();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file = new File("QUINCENA" + fa + ".xlsx");
		boolean fileExDownload = false;
		if (file.exists() && file.canWrite()) {
			fileExDownload = true;
		}
		String sortField = sort[0];
		String sortDirection = sort[1];
		Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Order order = new Order(direction, sortField);
		Pageable pageRequest = PageRequest.of(page - 1, size, Sort.by(order));
		Page<QuincenasEntity> quincena = null;
		QuincenaCatEntity quinShearch=this.quincenasCatJPA.findByIdQNA(filtrosSet.getQna());
		if(filtrosSet.getTipoContrato()!=null&&!filtrosSet.getTipoContrato().isEmpty()) {
			Collection<Empleados> collEmpleados = new HashSet<>();
			collEmpleados = empleadoRJPA.findByTipoContrato(filtrosSet.getTipoContrato());
			quincena=quincenaJPA.findByQuinCatAndEmpleadoQNIn(quinShearch,collEmpleados,pageRequest);
		}
			else if(filtrosSet.getStatus()!=null&&!filtrosSet.getStatus().isEmpty()) {
				boolean bajaStatus=false;
				if(filtrosSet.getStatus().equals("Baja"))
					bajaStatus=true;
				quincena = quincenaJPA.findByQuinCatAndBaja(quinShearch,bajaStatus,pageRequest);
			}
			else if(filtrosSet.getPuesto()!=null&&!filtrosSet.getPuesto().isEmpty()) {
				 PuestosEntity puesto=puestosJPA.findByPuesto(filtrosSet.getPuesto());
				 Collection<Empleados> collectionEmp = new ArrayList<Empleados>(puesto.getLEmpleados());
				 quincena=quincenaJPA.findByQuinCatAndEmpleadoQNIn(quinShearch,collectionEmp,pageRequest);
			}
			else if(filtrosSet.getServicio()!=null&&!filtrosSet.getServicio().isEmpty()) {
				ServiciosEntity servicio=this.serviciosJPA.findByServicio(filtrosSet.getServicio());
				Collection<Empleados> collectionEmp = new ArrayList<Empleados>(servicio.getLEmpleados());
				 quincena=quincenaJPA.findByQuinCatAndEmpleadoQNIn(quinShearch,collectionEmp,pageRequest);
			}
			else if(filtrosSet.getTipoPago()!=null&&!filtrosSet.getTipoPago().isEmpty()) {
				quincena = quincenaJPA.findByQuinCatAndTipoPago(quinShearch,filtrosSet.getTipoPago(),pageRequest);
		}else
			quincena = quincenaJPA.findByQuinCat(quinShearch,pageRequest);
		PageRender<QuincenasEntity> pageRender = new PageRender<>("/quincenas/listarQuincena", quincena);
		model.addAttribute("fileExDownload", fileExDownload);
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
		model.addAttribute("funLista", "quincenas/listarQuincena");
		model.addAttribute("funVer", "verXMLRec");
		model.addAttribute("funStatus", "statusXMLRec");
		model.addAttribute("registros",quincena.getTotalElements());
		model.addAttribute("quinCatSelectGet", filtrosSet.getQna());
		return "quincenas/listarQuincena";
	}
	@GetMapping("/quincenas/bloqueoQuincena/{id}")
	public String bloquearPago(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			QuincenasEntity quin=this.quincenaJPA.findById(id);
			quin.setBloqueoPago(true);
			this.quincenaJPA.save(quin);
			flash.addFlashAttribute("error", "Pago Bloqueado");
		}
		return "redirect:/quincenas/listarQuincena";
	}
	@GetMapping("/quincenas/desbloqueoQuincena/{id}")
	public String desbloqueoQuincena(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			QuincenasEntity quin=this.quincenaJPA.findById(id);
			quin.setBloqueoPago(false);
			this.quincenaJPA.save(quin);
			flash.addFlashAttribute("success", "Pago Activo");
		}
		return "redirect:/quincenas/listarQuincena";
	}
}
