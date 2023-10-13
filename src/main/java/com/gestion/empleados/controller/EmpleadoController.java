package com.gestion.empleados.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.entity.Empleado;
import com.gestion.empleados.service.EmpleadoService;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.reports.ExportExcel;
import com.gestion.empleados.utils.reports.ExporterPDF;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;
	
	@GetMapping({"/","/listar",""})
	public String listarEmpleados(@RequestParam(name="page",defaultValue="0") int page,Model model) {
		Pageable pageRequest= PageRequest.of(page, 3);
		Page<Empleado> empleados= empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender= new PageRender<>("/listar",empleados);
		model.addAttribute("titulo","Listado Empleados");
		model.addAttribute("empleados",empleados);
		model.addAttribute("page",pageRender);
		return "listar";
	}
	
	@GetMapping("/ver/{id}")
	public String verDetallesEmpleado(@PathVariable(value="id") Long id, Map<String,Object> modelo, RedirectAttributes flash) {
		Empleado empleado= empleadoService.findOne(id);
		if(empleado== null) {
			flash.addFlashAttribute("error","El empleado no Existe");
			return "redirect:/listar";
		}
		modelo.put("empleado",empleado);
		modelo.put("titulo","Detalles del Empleado" +empleado.getNombre());
		return "ver";
	}
	
	@GetMapping("/form")
	public String formularioRegistroEmpleado(Map<String,Object> modelo) {
		Empleado empleado = new Empleado();
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Registro Empleado");
		return "form";
	}
	
	@PostMapping("/form")
	public String guardaEmpleado(@Valid Empleado empleado,BindingResult result, Model modelo,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo","Registro de Empleado");
			return "form";
		}
		String mensaje= (empleado.getId()!=null)? "Empleado Actualizado con Exito" : "Empleado Registrado con Exito";
		empleadoService.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success",mensaje);
		return "redirect:/listar";
	}
	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value="id") Long id,Map<String,Object> modelo, RedirectAttributes flash) {
		Empleado empleado= null;
		if(id>0) {
			empleado=empleadoService.findOne(id);
			if(empleado==null) {
				flash.addFlashAttribute("error","El Empleado no Existe");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error","El Empleado no Existe");
			return "redirect:/listar";
		}
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Edicion de Empleado");
		return "form";
	}
	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value="id") Long id,RedirectAttributes flash) {
		if(id>0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("success","Empleado Eliminado con Exito");
		}
		return "redirect:/listar";
	}
	@GetMapping("/exportarPDF")
	public void exportEmpleadosPDF(HttpServletResponse respons) throws DocumentException, IOException {
		respons.setContentType("application/pdf");
		DateFormat df= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fa=df.format(new Date());
		String cabecera= "Content-Disposition";
		String valor="attachment; filename=Empleados_"+fa+".pdf";
		respons.setHeader(cabecera, valor);
		List<Empleado> lempeados=new ArrayList();
		ExporterPDF expdf= new ExporterPDF(lempeados);
		expdf.exportarPDF(respons);
	}
	@GetMapping("/exportarExcel")
	public void exportEmpleadosExcel(HttpServletResponse respons) throws DocumentException, IOException {
		respons.setContentType("application/octet-stream");
		DateFormat df= new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fa=df.format(new Date());
		String cabecera= "Content-Disposition";
		String valor="attachment; filename=Empleados_"+fa+".xlsx";
		respons.setHeader(cabecera, valor);
		List<Empleado> lempeados=new ArrayList();
		ExportExcel expdf= new ExportExcel(lempeados);
		expdf.exportarExcel(respons);
	}
}
