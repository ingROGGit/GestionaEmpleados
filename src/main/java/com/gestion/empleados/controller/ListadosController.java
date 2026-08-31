package com.gestion.empleados.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gestion.empleados.entity.CatCPJALEntity;
import com.gestion.empleados.entity.DeduccionesEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.PersepcionesEntity;
import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.ServiciosEntity;
import com.gestion.empleados.repository.CatCPJALRepositoryJPA;
import com.gestion.empleados.repository.DeduccionesRepositoryJPA;
import com.gestion.empleados.repository.PersepcionesRepositoryJPA;
import com.gestion.empleados.repository.PuestosRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;
import com.gestion.empleados.service.DeduccionesService;
import com.gestion.empleados.service.PersepcionesService;
import com.gestion.empleados.service.PuestosService;
import com.gestion.empleados.service.ServiciosService;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.reports.ExportExcel;
import com.gestion.empleados.utils.reports.ExportExcelThread;
import com.gestion.empleados.utils.reports.ExporterPDF;
import com.gestion.empleados.utils.reports.ExporterPDFCatalogos;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ListadosController {
	
	@Autowired
	private PuestosService puestosService;
	@Autowired
	private ServiciosRepositoryJPA serviciosJPA;
	@Autowired
	private PersepcionesService persepcionesService;
	@Autowired
	private DeduccionesService deduccionesService;
	
	@Autowired
	private PuestosRepositoryJPA puestosJPA;
	@Autowired
	private PersepcionesRepositoryJPA persepcionesJPA;
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private DeduccionesRepositoryJPA deduccionesJPA;
	@Autowired
	private CatCPJALRepositoryJPA catCPJALRepositoryJPA;
	@GetMapping("catalogos/listarPuestos")
	public String listarPuestos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<PuestosEntity> puestos = puestosService.findAll(pageRequest);
		PuestosEntity puesto= new PuestosEntity();
		PageRender<PuestosEntity> pageRender = new PageRender<>("/catalogos/listarPuestos", puestos);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Puestos_" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado Puestos");
		model.addAttribute("puestos", puestos);
		model.addAttribute("page", pageRender);
		return "catalogos/listarPuestos";
	}
	@GetMapping("catalogos/listarServicios")
	public String listarServicios(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
//		Page<ServiciosEntity> servicios = serviciosService.findAll(pageRequest);
//		serviciosJPA
		Page<ServiciosEntity> servicios = serviciosJPA.findAll(pageRequest);
		PageRender<ServiciosEntity> pageRender = new PageRender<>("/catalogos/listarServicios", servicios);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Servicios" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado Servicios");
		model.addAttribute("servicios", servicios);
		model.addAttribute("page", pageRender);
		return "catalogos/listarServicios";
	}
	@GetMapping("catalogos/listarPersepciones")
	public String listarPersepciones(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<PersepcionesEntity> persepciones = persepcionesService.findAll(pageRequest);
		PageRender<PersepcionesEntity> pageRender = new PageRender<>("/catalogos/listarPersepciones", persepciones);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Persepciones" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado Persepciones");
		model.addAttribute("persepciones", persepciones);
		model.addAttribute("page", pageRender);
		return "catalogos/listarPersepciones";
	}
	@GetMapping("catalogos/listarDeducciones")
	public String listarDeducciones(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<DeduccionesEntity> deducciones = deduccionesService.findAll(pageRequest);
		PageRender<DeduccionesEntity> pageRender = new PageRender<>("/catalogos/listarDeducciones", deducciones);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Deducciones" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado Deducciones");
		model.addAttribute("deducciones", deducciones);
		model.addAttribute("page", pageRender);
		return "catalogos/listarDeducciones";
	}
	
	@GetMapping("catalogos/listarQNACAT")
	public String listarQNACAT(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<QuincenaCatEntity> quincenasCat = quincenasCatJPA.findAll(pageRequest);
		PageRender<QuincenaCatEntity> pageRender = new PageRender<>("/catalogos/listarQNACAT", quincenasCat);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("QNACAT" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado QUINCENAS");
		model.addAttribute("quincenasCat", quincenasCat);
		model.addAttribute("page", pageRender);
		return "catalogos/listarQNACAT";
	}
	@GetMapping("catalogos/listarCCPJAL")
	public String listarCCPJAL(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<CatCPJALEntity> catCPJ = catCPJALRepositoryJPA.findAll(pageRequest);
		PageRender<CatCPJALEntity> pageRender = new PageRender<>("/catalogos/listarCCPJAL", catCPJ);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("CCPJAL" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado QUINCENAS");
		model.addAttribute("catCPJ", catCPJ);
		model.addAttribute("page", pageRender);
		return "catalogos/listarCCPJAL";
	}
	@GetMapping("/catalogos/exportarPDF")
	public void exportEmpleadosPDF(@RequestParam("TIPOEXPOR") String TIPOEXPOR,HttpServletResponse respons) throws DocumentException, IOException {
		respons.setContentType("application/pdf");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fa = df.format(new Date());
		String cabecera = "Content-Disposition";
		String valor ="";
		if(TIPOEXPOR.equals("CPU"))
			valor="attachment; filename=PUESTOS_" + fa + ".pdf";
		if(TIPOEXPOR.equals("CSE"))
			valor="attachment; filename=SERVICIOS_" + fa + ".pdf";
		if(TIPOEXPOR.equals("CDED"))
			valor="attachment; filename=DEDUCCIONES_" + fa + ".pdf";
		if(TIPOEXPOR.equals("CPER"))
			valor="attachment; filename=PERSEPCIONES_" + fa + ".pdf";
		if(TIPOEXPOR.equals("CQNA"))
			valor="attachment; filename=QUINCENAS_" + fa + ".pdf";
		
		respons.setHeader(cabecera, valor);
		ExporterPDFCatalogos exporPDFCat;
		if(TIPOEXPOR.equals("CPU")) {
			List<PuestosEntity> list = puestosJPA.findAll();
			exporPDFCat = new ExporterPDFCatalogos(list,TIPOEXPOR);
			exporPDFCat.exportarPDF(respons,TIPOEXPOR,"PUESTOS");
		}
		if(TIPOEXPOR.equals("CSE")) {
			List<ServiciosEntity> list = serviciosJPA.findAll();
			exporPDFCat = new ExporterPDFCatalogos(list,TIPOEXPOR);
			exporPDFCat.exportarPDF(respons,TIPOEXPOR,"SERVICIOS");
		}
		if(TIPOEXPOR.equals("CDED")) {
			List<DeduccionesEntity> list = deduccionesJPA.findAll();
			exporPDFCat = new ExporterPDFCatalogos(list,TIPOEXPOR);
			exporPDFCat.exportarPDF(respons,TIPOEXPOR,"DEDUCCIONES");
		}
		if(TIPOEXPOR.equals("CPER")) {
			List<PersepcionesEntity> list = persepcionesJPA.findAll();
			exporPDFCat = new ExporterPDFCatalogos(list,TIPOEXPOR);
			exporPDFCat.exportarPDF(respons,TIPOEXPOR,"PERSEPCIONES");
		}
		if(TIPOEXPOR.equals("CQNA")) {
			List<QuincenaCatEntity> list = quincenasCatJPA.findAll();
			exporPDFCat = new ExporterPDFCatalogos(list,TIPOEXPOR);
			exporPDFCat.exportarPDF(respons,TIPOEXPOR,"QUINCENAS");
		}
		
	}

	@GetMapping("/catalogos/exportarExcel")
	public void exportEmpleadosExcel(@RequestParam("TIPOEXPOR") String TIPOEXPOR,HttpServletResponse respons) throws DocumentException, IOException {
		respons.setContentType("application/octet-stream");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fa = df.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fa + ".xlsx";
		respons.setHeader(cabecera, valor);
//		List<Empleados> lempeados = empleadoService.findAll();
//		ExportExcel exexcel = new ExportExcel();
//		exexcel.ExportExcel(lempeados);
//		exexcel.exportarExcel(respons,"Empleados");
	}
	@GetMapping("/catalogos/exportarExcelThread")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportEmpleadosExcelThread(@RequestParam("TIPOEXPOR") String TIPOEXPOR) throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Empleados_" + fa + ".xlsx");
//		List<Empleados> lempeados = empleadoService.findAll();
//		ExportExcelThread exexcel = new ExportExcelThread(file.getName(), "Empleados", null, null, lempeados);
//		exexcel.setName("Export-Empleados");
//		exexcel.setPriority(Thread.MAX_PRIORITY);
//		exexcel.start();
	}
}
