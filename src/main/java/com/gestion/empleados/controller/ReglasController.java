package com.gestion.empleados.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.repository.ReglasDiasRepository;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.reports.ExportExcel;
import com.gestion.empleados.utils.reports.ExporterPDF;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class ReglasController {
	@Autowired
	ReglasDiasRepository reglasRJPA;

	@GetMapping("reglas/listarReglas")
	public String listarreglas(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 50);
		Page<ReglasDiasEntity> reglas = reglasRJPA.findAll(pageRequest);
		PageRender<ReglasDiasEntity> pageRender = new PageRender<>("/reglas/listarreglas", reglas);
		model.addAttribute("titulo", "Listado reglas");
		model.addAttribute("reglas", reglas);
		model.addAttribute("page", pageRender);
		return "reglas/listarReglas";
	}

	@GetMapping("reglas/formReglas")
	public String formularioRegistroRegla(Map<String, Object> modelo) {
		ReglasDiasEntity regla = new ReglasDiasEntity();
		modelo.put("regla", regla);
		modelo.put("titulo", "Registro Empleado");
		return "reglas/formRegla";
	}

	@PostMapping("reglas/formReglas")
	public String guardaRegla(@Valid ReglasDiasEntity regla, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Reglas");
			return "reglas/formRegla";
		}
		flash.addFlashAttribute("success", "Regla Registrada con Exito");
		reglasRJPA.save(regla);
		status.setComplete();
		regla = new ReglasDiasEntity();
		modelo.addAttribute("regla", regla);
		return "reglas/formRegla";
	}
	@PostMapping("reglas/updateRegla")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String updateRegla(@Valid ReglasDiasEntity regla, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		reglasRJPA.save(regla);
		flash.addFlashAttribute("success", "Regla Actualizada con Exito");
		status.setComplete();
		return "redirect:/reglas/listarReglas";
	}

	@GetMapping("/reglas/formRegla/{id}")
	public String editarRegla(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		ReglasDiasEntity regla = null;
		if (id > 0) {
			regla = reglasRJPA.findById(id);
			if (regla == null) {
				flash.addFlashAttribute("error", "La regla no Existe");
				return "redirect:/reglas/listarReglas";
			}
		} else {
			flash.addFlashAttribute("error", "La regla no Existe");
			return "redirect:/listar";
		}
		modelo.put("regla", regla);
		modelo.put("titulo", "Edicion de Reglas");
		return "reglas/formReglaModal";
	}

	@GetMapping("/reglas/eliminar/{id}")
	public String eliminarRegla(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			reglasRJPA.deleteById(id);
			flash.addFlashAttribute("success", "Regla Eliminada con Exito");
		}
		return "redirect:/reglas/listarReglas";
	}

//	@GetMapping("/reglas/exportarPDF")
//	public void exportreglasPDF(HttpServletResponse respons) throws DocumentException, IOException {
//		respons.setContentType("application/pdf");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//		String fa = df.format(new Date());
//		String cabecera = "Content-Disposition";
//		String valor = "attachment; filename=reglas_" + fa + ".pdf";
//		respons.setHeader(cabecera, valor);
//		List<ReglasDiasEntity> lReglas = reglasRJPA.findAll();
//		ExporterPDF expdf = new ExporterPDF(lReglas);
//		expdf.exportarPDF(respons);
//	}
//
//	@GetMapping("/reglas/exportarExcel")
//	public void exportreglasExcel(HttpServletResponse respons) throws DocumentException, IOException {
//		respons.setContentType("application/octet-stream");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//		String fa = df.format(new Date());
//		String cabecera = "Content-Disposition";
//		String valor = "attachment; filename=reglas_" + fa + ".xlsx";
//		respons.setHeader(cabecera, valor);
//		List<ReglasDiasEntity> lReglas = reglasRJPA.findAll();
//		ExportExcel expdf = new ExportExcel(lReglas);
//		expdf.exportarExcel(respons);
//	}
}
