package com.gestion.empleados.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
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

import com.gestion.empleados.entity.AltaCuentasFilesEntity;
import com.gestion.empleados.entity.BancosEntity;
import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.repository.AltaCuentasFileRepository;
import com.gestion.empleados.repository.BancosRepositoryJPA;
import com.gestion.empleados.repository.CuentasRepositoryJPA;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.reports.ExportCuentasBanco;
import com.lowagie.text.DocumentException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class CuentasBancariasController {
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private CuentasRepositoryJPA cuentasJPA;
	@Autowired
	private BancosRepositoryJPA bancosJPA;
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private AltaCuentasFileRepository acfileJPA;
	@GetMapping({ "/cuentasBancarias/ListaEmpSinCuenta"})
	public String ListaEmpSinCuenta(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<Empleados> empleados = empleadosJPA.findByLCuentasIsEmpty(pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/empleados/listarEmpleados", empleados);
		boolean fileExDownload=false;
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Empleados sin Cuenta");
		model.addAttribute("registros",empleados.getTotalElements());
		return "cuentasBancarias/ListaEmpSinCuenta";
	}
	
	@GetMapping("cuentasBancarias/listarEmpleadosSinTel")
	public String listarEmpleadosSinTel(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<Empleados> empleados = empleadosJPA.findByTelefonoIsNull(pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/cuentasBancarias/listarEmpleadosSinTel", empleados);
		boolean fileExDownload=false;
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado Empleados sin Telefono");
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("registros",empleados.getTotalElements());
		model.addAttribute("addNew","SI");
		return "cuentasBancarias/listarEmpleadosSinTel";
	}
	@PostMapping("cuentasBancarias/formEmpleado")
	public String guardaEmpleado(@Valid Empleados empleado, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Empleado");
			return "empleados/formEmpleado";
		}
		String mensaje = (empleado.getId() != null) ? "Empleado Actualizado con Exito"
				: "Empleado Registrado con Exito";
		this.empleadosJPA.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/cuentasBancarias/listarEmpleadosSinTel";
	}
	@GetMapping("/cuentasBancarias/formEmpleado/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleados empleado = null;
		if (id > 0) {
			empleado = this.empleadosJPA.findById(id);
			if (empleado == null) {
				flash.addFlashAttribute("error", "El Empleado no Existe");
				return "redirect:/empleados/listarEmpleados";
			}
		} else {
			flash.addFlashAttribute("error", "El Empleado no Existe");
			return "redirect:/listar";
		}
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Edicion de Empleado");
		return "cuentasBancarias/formEmpleadoModal";
	}
	@GetMapping({ "/cuentasBancarias/ListaFilesCuenta"})
	public String ListaFilesCuenta(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<AltaCuentasFilesEntity> lFileCuentas = this.acfileJPA.findAll(pageRequest);
		PageRender<AltaCuentasFilesEntity> pageRender = new PageRender<>("/cuentasBancarias/ListaFilesCuenta", lFileCuentas);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("FilesCuentas" + fa + ".xlsx");
		System.out.println(file.getAbsolutePath());
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("files", lFileCuentas);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Empleados sin Cuenta");
		model.addAttribute("registros",lFileCuentas.getTotalElements());
		return "cuentasBancarias/ListaFilesCuenta";
	}
	@GetMapping("cuentasBancarias/verCuenta/{id}")
	public String verDetallesEmpleado(@PathVariable(value = "id") Long id,@RequestParam(value = "quincena") String idQNA, Map<String, Object> modelo,
			RedirectAttributes flash) {
		CuentasEntity cuenta = this.cuentasJPA.findByEmpleadoC_Id(id);
		Empleados emp=this.empleadosJPA.findById(id);
		modelo.put("cuenta", cuenta);
		modelo.put("titulo", "Detalles de la Cuenta " + emp.getNombre());
		return "cuentasBancarias/verCuenta";
	}
	@GetMapping("/cuentasBancarias/formCuenta/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id,@RequestParam(required = false) String addNew, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleados emp=this.empleadosJPA.findById(id);
		CuentasEntity cuenta;
		if(addNew!=null) {
			cuenta= new CuentasEntity();
			cuenta.setEmpleadoC(emp);
			cuenta.setEstatus("En Registro");
		}else {
		cuenta = this.cuentasJPA.findByEmpleadoC_Id(id);
		if(cuenta==null) {
			cuenta= new CuentasEntity();
			cuenta.setEmpleadoC(emp);
			cuenta.setEstatus("En Registro");
		}
		}
		List<BancosEntity> LBancos=this.bancosJPA.findAll();
		modelo.put("cuenta", cuenta);
		modelo.put("LBancos", LBancos);
		modelo.put("titulo", "Registrar Cuenta");
		return "cuentasBancarias/formCuentaModal";
	}
	@PostMapping("cuentasBancarias/formCuenta")
	public String guardaEmpleado(@Valid CuentasEntity cuenta, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Cuenta");
			return "cuentasBancarias/formCuenta";
		}
		cuenta.setFechaAlta(new Date());
		String mensaje = "Cuenta Registrada con Exito";
		this.cuentasJPA.save(cuenta);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/cuentasBancarias/ListaEmpSinCuenta";
	}
	
	@GetMapping("/cuentasBancarias/UPCuentas")
	public String UPCuentas(@RequestParam(name = "page", defaultValue = "0") int page,Model model) throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		Pageable pageRequest = PageRequest.of(page, 100);
		Page<Empleados> empleados = empleadosJPA.findByLCuentasEstatus("En Registro",pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/cuentasBancarias/ListaEmpSinCuenta", empleados);
		List<String> LquincenasCat = quincenasCatJPA.findByAllIdQNA();
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = quincenasCatJPA.findQNAACT(fechaSqlHoy);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Alta Cuentas");
		model.addAttribute("empleados", empleados);
		model.addAttribute("alta", fa);
		model.addAttribute("quinCatSelect",quincenasCat);
		model.addAttribute("LquincenasCat",LquincenasCat);
		model.addAttribute("registros",empleados.getTotalElements());
		return "cuentasBancarias/UPCuentas";
	}
	@PostMapping("/cuentasBancarias/WriteFile")
	public void FileExcel(HttpServletResponse respons,@RequestParam("alta") String alta,@RequestParam("quinCatSelect") String quinCatSelect) throws Exception {
		try {
		File file=new File(alta + ".xlsx");
		List<Empleados> lEmpleadosCuentas=this.empleadosJPA.findByLCuentasEstatus("En Registro");
		ExportCuentasBanco excelCuentas =new ExportCuentasBanco(alta, lEmpleadosCuentas,quinCatSelect);
		excelCuentas.run();
		if(file.exists() &&file.canWrite())
		{
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=" + file.getName();
		respons.setHeader(cabecera, valor);
		try (InputStream inputStream = new FileInputStream(file);
	             ServletOutputStream outputStream = respons.getOutputStream()) {
	            IOUtils.copy(inputStream, outputStream);
	            respons.flushBuffer();
	            inputStream.close();
	            AltaCuentasFilesEntity acfile=AltaCuentasFilesEntity.builder().alta(alta)
	            		.fechaAlta(new Date()).fileAlta(Files.readAllBytes(file.toPath()))
	            		.build();
	            this.acfileJPA.save(acfile);
	            file.delete();
	            for(Empleados emp:lEmpleadosCuentas) {
	            	CuentasEntity cuenta=emp.getLCuentas().get(0);
	            	cuenta.setEstatus(alta);
	            	this.cuentasJPA.save(cuenta);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error writing file to response", e);
	        }
		}
		}catch(Exception err) {
			respons.getWriter().write(
					err.getMessage().length() > 50 ? err.getMessage().substring(0, 50) : err.getMessage());
			throw new Exception(err);
		}
	}
	@GetMapping("/cuentasBancarias/download/{id}")
	public void download(HttpServletResponse respons,@PathVariable(value = "id") Long id) throws DocumentException, IOException {
		AltaCuentasFilesEntity fileCuenta=this.acfileJPA.findById(id);
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=" + fileCuenta.getAlta()+".xlsx";
		respons.setHeader(cabecera, valor);
		try (InputStream inputStream = new ByteArrayInputStream(fileCuenta.getFileAlta());
	             ServletOutputStream outputStream = respons.getOutputStream()) {
	            IOUtils.copy(inputStream, outputStream);
	            respons.flushBuffer();
	            inputStream.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error writing file to response", e);
	        }
	}
	@GetMapping("/cuentasBancarias/formFile/{id}")
	public String upFechaFile(@PathVariable(value = "id") Long id,@RequestParam(required = false) String addNew, Map<String, Object> modelo,
			RedirectAttributes flash) {
		AltaCuentasFilesEntity cuantasFile=this.acfileJPA.findById(id);
		modelo.put("cuantasFile", cuantasFile);
		modelo.put("titulo", "Registrar Fecha");
		return "cuentasBancarias/formFileFechaModal";
	}
	@PostMapping("cuentasBancarias/formFile")
	public String upFechaFilePost(@Valid AltaCuentasFilesEntity cuantasFile, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			flash.addFlashAttribute("error", result);
			return "redirect:/cuentasBancarias/ListaFilesCuenta";
		}
		String mensaje = "Fecha Registrada "+cuantasFile.getAlta();
		this.acfileJPA.save(cuantasFile);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/cuentasBancarias/ListaFilesCuenta";
	}
}
