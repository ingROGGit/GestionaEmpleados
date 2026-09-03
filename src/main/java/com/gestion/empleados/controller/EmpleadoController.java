package com.gestion.empleados.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.VacacionesEntity;
import com.gestion.empleados.repository.BancosRepositoryJPA;
import com.gestion.empleados.repository.CatCPJALRepositoryJPA;
import com.gestion.empleados.repository.DeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetalleDeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetallePersepcionesRepositoryJPA;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.PersepcionesRepositoryJPA;
import com.gestion.empleados.repository.PuestosRepositoryJPA;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.ReglasDiasRepository;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;
import com.gestion.empleados.repository.TurnosRepositoryJPA;
import com.gestion.empleados.repository.VacacionesRepository;
import com.gestion.empleados.service.EmpleadoService;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.ProcesaFileXLSXThread;
import com.gestion.empleados.utils.reports.ExportExcel;
import com.gestion.empleados.utils.reports.ExportExcelThread;
import com.gestion.empleados.utils.reports.ExporterPDF;
import com.lowagie.text.DocumentException;
import org.springframework.http.HttpStatus;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private TurnosRepositoryJPA trunosRJPA;
	@Autowired
	private VacacionesRepository vacacionesRJPA;
	@Autowired
	private ReglasDiasRepository reglasDiasRJPA;
	@Autowired
	private EmpleadosRepositoryJPA  empleadoRJPA;
	@Autowired
	private PuestosRepositoryJPA puestosJPA;
	@Autowired
	private ServiciosRepositoryJPA serviciosJPA;
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private PersepcionesRepositoryJPA persepcionesJPA;
	@Autowired
	private DeduccionesRepositoryJPA deduccionesJPA;
	@Autowired
	private BancosRepositoryJPA bancosJPA;
	@Autowired
	private CatCPJALRepositoryJPA catCPJALRepositoryJPA;
	@Autowired
	private DetallePersepcionesRepositoryJPA detallePerJPA;
	@Autowired
	private DetalleDeduccionesRepositoryJPA detalleDedJPA;
	@Autowired
	private QuincenaRepositoryJPA quincenaJPA;
	private final ProcesaFileXLSXThread thread;
	 public EmpleadoController(ProcesaFileXLSXThread procesaFileXLSXThread) {
	        this.thread = procesaFileXLSXThread;
	    }
	@GetMapping({ "/", "/start", "" })
	public String menu(Model model) {
		model.addAttribute("titulo", "Inicio");
		return "start";
	}
	@GetMapping({ "/contacto"})
	public String contacto(Model model) {
		model.addAttribute("titulo", "Contacto");
		return "contacto";
	}
	@GetMapping({"/quien"})
	public String quien(Model model) {
		model.addAttribute("titulo", "Quienes Somos");
		return "quien";
	}
	@GetMapping("empleados/listarEmpleados")
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<Empleados> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/empleados/listarEmpleados", empleados);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Empleados_" + fa + ".xlsx");
		boolean fileExDownload=false;
		if(file.exists() &&file.canWrite())
		{
			fileExDownload=true;
		}
		model.addAttribute("fileExDownload",fileExDownload);
		model.addAttribute("titulo", "Listado Empleados");
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		return "empleados/listarEmpleados";
	}

	@GetMapping("empleados/verEmpleado/{id}")
	public String verDetallesEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
//		Empleados empleado = empleadoService.findOne(id);
		Empleados empleado = empleadoRJPA.findById(id);
		LocalDate hoy = LocalDate.now();
		LocalDate fechaIngreso = new java.sql.Date(empleado.getFechaIngreso().getTime()).toLocalDate();
		Period periodo = Period.between(fechaIngreso, hoy);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El empleado no Existe");
			return "redirect:/empleados/listarEmpleados";
		}
		modelo.put("periodo", periodo);
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Detalles del Empleado " + empleado.getNombre());
		return "empleados/verEmpleadoModal";
	}

	@GetMapping("empleados/formEmpleado")
	public String formularioRegistroEmpleado(Map<String, Object> modelo) {
		Empleados empleado = new Empleados();
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Registro Empleado");
		return "empleados/formEmpleado";
	}

	@PostMapping("empleados/formEmpleado")
	public String guardaEmpleado(@Valid Empleados empleado, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Empleado");
			return "empleados/formEmpleado";
		}
		String mensaje = (empleado.getId() != null) ? "Empleado Actualizado con Exito"
				: "Empleado Registrado con Exito";
		empleadoService.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/empleados/listarEmpleados";
	}

	@GetMapping("/empleados/formEmpleado/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleados empleado = null;
		if (id > 0) {
			empleado = empleadoService.findOne(id);
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
		return "empleados/formEmpleadoModal";
	}

	@GetMapping("/empleados/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("success", "Empleado Eliminado con Exito");
		}
		return "redirect:/empleados/listarEmpleados";
	}

	@GetMapping("/empleados/exportarPDF")
	public void exportEmpleadosPDF(HttpServletResponse respons) throws DocumentException, IOException {
		respons.setContentType("application/pdf");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fa = df.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fa + ".pdf";
		respons.setHeader(cabecera, valor);
		List<Empleados> lempeados = empleadoService.findAll();
		ExporterPDF expdf = new ExporterPDF(lempeados);
		expdf.exportarPDF(respons);
	}

	@GetMapping("/empleados/exportarExcel")
	public void exportEmpleadosExcel(HttpServletResponse respons) throws DocumentException, IOException {
		respons.setContentType("application/octet-stream");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fa = df.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fa + ".xlsx";
		respons.setHeader(cabecera, valor);
		List<Empleados> lempeados = empleadoService.findAll();
		ExportExcel exexcel = new ExportExcel();
		exexcel.ExportExcel(lempeados);
		exexcel.exportarExcel(respons,"Empleados");
	}
	@GetMapping("/empleados/exportarExcelThread")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportEmpleadosExcelThread() throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Empleados_" + fa + ".xlsx");
		List<Empleados> lempeados = empleadoService.findAll();
		ExportExcelThread exexcel = new ExportExcelThread(file.getName(), "Empleados", null, null, lempeados);
		exexcel.setName("Export-Empleados");
		exexcel.setPriority(Thread.MAX_PRIORITY);
		exexcel.start();
	}
	@GetMapping("/empleados/FileExcel")
	public String FileExcel(HttpServletResponse respons) throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file=new File("Empleados_" + fa + ".xlsx");
		if(file.exists() &&file.canWrite())
		{
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fa + ".xlsx";
		respons.setHeader(cabecera, valor);
		try (InputStream inputStream = new FileInputStream(file);
	             ServletOutputStream outputStream = respons.getOutputStream()) {
	            IOUtils.copy(inputStream, outputStream);
	            respons.flushBuffer();
	            inputStream.close();
	            file.delete();
	            return "redirect:/empleados/listarEmpleados";
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error writing file to response", e);
	        }
		}
		else {
			return "redirect:/empleados/listarEmpleados";
		}
	}
	@GetMapping("empleados/AddEXLSX")
	public String addXLSX(@RequestParam("TIPOCARGA") String TIPOCARGA,Model model) {
		Map<String, String> CatalogoCarga = new HashMap();
		List<String> lquincenas = quincenasCatJPA.findByAllIdQNA();
		CatalogoCarga.put("CEM", "Carga de Empleados");
		CatalogoCarga.put("CBA", "Carga de Bancos");
		CatalogoCarga.put("CCU", "Carga de Cuentas");
		CatalogoCarga.put("CPU", "Carga de Puestos");
		CatalogoCarga.put("CSE", "Carga de Servicios");
		CatalogoCarga.put("CPER", "Carga de Persepciones");
		CatalogoCarga.put("CDED", "Carga de Deducciones");
		CatalogoCarga.put("CQNA", "Carga de Quincena");
		CatalogoCarga.put("CPRE", "Carga de Prenomina");
		CatalogoCarga.put("CNOM", "Carga de Nomina");
		CatalogoCarga.put("CCP", "Carga Codigos Postales");
		model.addAttribute("titulo", "EXCEL "+CatalogoCarga.get(TIPOCARGA));
		model.addAttribute("TIPOCARGA", TIPOCARGA);
		model.addAttribute("quin", "");
		model.addAttribute("lquincenas", lquincenas);
		model.addAttribute("CatalogoCarga", CatalogoCarga);
		return "empleados/AddEmpleados";
	}
	@PostMapping("empleados/addXLSX")
	public String addEmpleadosXLSX(Model modelo, RedirectAttributes flash, SessionStatus status,
			@RequestParam("fileXLS") MultipartFile fileXLS,@RequestParam("TIPOCARGA") String TIPOCARGA,@RequestParam("CatalogoCarga") String CatalogoCarga,@RequestParam(required = false) String quin) {
		try {
			File filewrite = new File(fileXLS.getOriginalFilename());
			try (FileOutputStream fos = new FileOutputStream(filewrite)) {
				fos.write(fileXLS.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
//			ProcesaFileXLSXThread thread = new ProcesaFileXLSXThread(empleadosJPA,trunosRJPA,vacacionesRJPA,reglasDiasRJPA,puestosJPA,
//					serviciosJPA,quincenasCatJPA,persepcionesJPA,deduccionesJPA,bancosJPA,catCPJALRepositoryJPA,detallePerJPA,detalleDedJPA,quincenaJPA);
			this.thread.run(filewrite,TIPOCARGA,quin);
			modelo.addAttribute("success", "Archivo cargado Satisfactoriamente se prosesaran en segundo plano");
		} catch (Exception err) {
			err.printStackTrace();
			modelo.addAttribute("error",
					err.getMessage().length() > 50 ? err.getMessage().substring(0, 50) : err.getMessage());
		}
		return "empleados/AddEmpleados";
	}
}
