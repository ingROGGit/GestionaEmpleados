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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Set;
import java.util.stream.Collectors;

import com.gestion.empleados.entity.AsignacionTurnoEntity;
import com.gestion.empleados.entity.DetalleDeduccionesEntiy;
import com.gestion.empleados.entity.DetallePersepcionesEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.SINAVIDEntity;
import com.gestion.empleados.entity.VacacionesEntity;
import com.gestion.empleados.repository.AsignacionTurnosRepositoryJPA;
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
import com.gestion.empleados.repository.SINAVIDRepositoryJPA;
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
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private TurnosRepositoryJPA trunosRJPA;
	@Autowired
	private VacacionesRepository vacacionesRJPA;
	@Autowired
	private ReglasDiasRepository reglasDiasRJPA;
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
	@Autowired
	private SINAVIDRepositoryJPA sinavidJPA;
	@Autowired
	private AsignacionTurnosRepositoryJPA asignaTurJPA;
	private final ProcesaFileXLSXThread thread;

	public EmpleadoController(ProcesaFileXLSXThread procesaFileXLSXThread) {
		this.thread = procesaFileXLSXThread;
	}

	@GetMapping({ "/", "/start", "" })
	public String menu(Model model) {
		model.addAttribute("titulo", "Inicio");
		return "start";
	}

	@GetMapping({ "/contacto" })
	public String contacto(Model model) {
		model.addAttribute("titulo", "Contacto");
		return "contacto";
	}

	@GetMapping({ "/quien" })
	public String quien(Model model) {
		model.addAttribute("titulo", "Quienes Somos");
		return "quien";
	}

	@GetMapping("empleados/listarEmpleados")
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<Empleados> empleados = this.empleadosJPA.findAll(pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/empleados/listarEmpleados", empleados);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File dirUsu = new File(SecurityContextHolder.getContext().getAuthentication().getName());
		File file = new File(dirUsu + "\\Empleados_" + fa + ".xlsx");
		boolean fileExDownload = false, filefull = false;
		if (file.exists()) {
			if (file.canWrite()) {
				fileExDownload = true;
				filefull = true;
			} else
				filefull = false;

		}
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = this.quincenasCatJPA.findQNAACT(fechaSqlHoy);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		model.addAttribute("fileExDownload", fileExDownload);
		model.addAttribute("filefull", filefull);
		model.addAttribute("titulo", "Listado Empleados");
		model.addAttribute("empleados", empleados);
		model.addAttribute("registros", empleados.getSize());
		model.addAttribute("page", pageRender);
		model.addAttribute("addNew", "SI");
		model.addAttribute("quinCatSelectGet", quincenasCat);
		model.addAttribute("LquincenasCat", LquincenasCat);
		return "empleados/listarEmpleados";
	}

	@GetMapping("empleados/verEmpleado/{id}")
	public String verDetallesEmpleado(@PathVariable(value = "id") Long id,
			@RequestParam(value = "quincena") String idQNA, Map<String, Object> modelo, RedirectAttributes flash) {
//		Empleados empleado = empleadoService.findOne(id);
		Empleados empleado = this.empleadosJPA.findById(id);
		QuincenasEntity quincena = this.empleadosJPA.findQuincenaByEmpleadoAndCatId(id, idQNA);
		List<DetallePersepcionesEntity> detalleper = this.detallePerJPA
				.findByEmpleadoPerAndQuincenaCatDP_IdQNA(empleado, idQNA);
		List<DetalleDeduccionesEntiy> detalleded = this.detalleDedJPA.findByEmpleadoDDAndQuincenaCatDD_IdQNA(empleado,
				idQNA);
		List<AsignacionTurnoEntity> asignacionTH=this.asignaTurJPA.findByEmpleado_Id(id);
		LocalDate hoy = LocalDate.now();
		LocalDate fechaIngreso = new java.sql.Date(empleado.getFechaIngreso().getTime()).toLocalDate();
		Period periodo = Period.between(fechaIngreso, hoy);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El empleado no Existe");
			return "redirect:/empleados/listarEmpleados";
		}
		modelo.put("periodo", periodo);
		modelo.put("empleado", empleado);
		modelo.put("quincena", quincena);
		modelo.put("detalleper", detalleper);
		modelo.put("detalleded", detalleded);
		modelo.put("asignacionTH",asignacionTH);
		modelo.put("titulo", "Detalles del Empleado " + empleado.getNombre());
		return "empleados/verEmpleadoModal";
	}

	@GetMapping("empleados/verDatosEmpleado/{id}")
	public String verDatosEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleados empleado = this.empleadosJPA.findById(id);
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

	@PostMapping("empleados/formEmpleadoSave")
	public String guardaEmpleado(@Valid Empleados empleado, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Empleado");
			return "empleados/formEmpleadoModal";
		}
		Empleados getEmpleado = this.empleadosJPA.findById(empleado.getId());
		getEmpleado.setSexo(empleado.getSexo());
		getEmpleado.setCorreo(empleado.getCorreo());
		getEmpleado.setTelefono(empleado.getTelefono());
		getEmpleado.setTelefonoEmer(empleado.getTelefonoEmer());
		// 1. Obtener la autenticación actual
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 2. Extraer los nombres de los roles
		Set<String> roles = authentication.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toSet());
		boolean grabar = false;
		for (String rol : roles) {
			if (rol.equals("ROLE_ADMIN") || rol.equals("ROLE_ADMIN1") || rol.equals("ROLE_OPERADORCUENTAS")
					|| rol.equals("ROLE_OPERADORSINAVID") || rol.equals("ROLE_OPERADORSINAVID1"))
				grabar = true;
		}
		if (grabar) {
			getEmpleado.setFechaIngreso(empleado.getFechaIngreso());
			getEmpleado.setFechaIngresoH(empleado.getFechaIngresoH());
			getEmpleado.setTipoContrato(empleado.getTipoContrato());
			getEmpleado.setCurp(empleado.getCurp());
			getEmpleado.setRfc(empleado.getRfc());
			getEmpleado.setNombre(empleado.getNombre());
			getEmpleado.setApellidop(empleado.getApellidop());
			getEmpleado.setApellidom(empleado.getApellidom());
			getEmpleado.setNombreCompleto(empleado.getApellidop()+" "+empleado.getApellidom()+" "+empleado.getNombre());
			SINAVIDEntity sinavid = getEmpleado.getSinavid();
			if (sinavid != null) {
				sinavid.setEstatus("CORREGIDO");
				sinavid.setError(null);
				sinavid.setFechaRespuesta(null);
				this.sinavidJPA.save(sinavid);
			}
		}
		String mensaje = (empleado.getId() != null) ? "Empleado Actualizado con Exito"
				: "Empleado Registrado con Exito";
		this.empleadosJPA.save(getEmpleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/empleados/listarEmpleados";
	}

	@GetMapping("/empleados/formEmpleadoEdit/{id}")
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
		return "empleados/formEmpleadoModal";
	}

	@GetMapping("/empleados/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			this.empleadosJPA.delete(this.empleadosJPA.findById(id));
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
		List<Empleados> lempeados = this.empleadosJPA.findAll();
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
		List<Empleados> lempeados = this.empleadosJPA.findAll();
		ExportExcel exexcel = new ExportExcel();
		exexcel.ExportExcel(lempeados);
		exexcel.exportarExcel(respons, "Empleados");
	}

	@GetMapping("/empleados/exportarExcelThread")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void exportEmpleadosExcelThread(@RequestParam("quinCatSelectGet") String quinCatSelectGet)
			throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File dirUsu = new File(SecurityContextHolder.getContext().getAuthentication().getName());
		if (!dirUsu.exists())
			dirUsu.mkdirs();
		File file = new File(dirUsu + "\\Empleados_" + fa + ".xlsx");
		List<Empleados> lempeados = this.empleadosJPA.findByEMpleadosXQuincena(quinCatSelectGet);
		for (Empleados e : lempeados) {
			if (e.getPuestosEntity() != null)
				e.getPuestosEntity().getPuesto();
			if (e.getServicioEntity() != null)
				e.getServicioEntity().getServicio();
		}
		ExportExcelThread exexcel = new ExportExcelThread(file.getAbsolutePath(), "Empleados", null, null, lempeados);
		exexcel.setName("Export-Empleados");
		exexcel.setPriority(Thread.MAX_PRIORITY);
		exexcel.start();
	}

	@GetMapping("/empleados/FileExcel")
	public String FileExcel(HttpServletResponse respons) throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File dirUsu = new File(SecurityContextHolder.getContext().getAuthentication().getName());
		File file = new File(dirUsu + "\\Empleados_" + fa + ".xlsx");
		if (file.exists() && file.canWrite()) {
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
		} else {
			return "redirect:/empleados/listarEmpleados";
		}
	}

	@GetMapping("empleados/AddEXLSX")
	public String addXLSX(@RequestParam("TIPOCARGA") String TIPOCARGA, Model model) {
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quinCatSelectPost = quincenasCatJPA.findQNAACT(fechaSqlHoy);
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
		CatalogoCarga.put("CALCU", "Carga Alta de Cuentas");
		CatalogoCarga.put("CALSINAVID", "Carga Alta SINAVID");
		CatalogoCarga.put("CEMTEL", "Carga Telefonos");
		CatalogoCarga.put("CEMCURP", "Carga CURP RFC");
		CatalogoCarga.put("CEXTSINAVID", "Carga Extracto");
		CatalogoCarga.put("CERRSINAVID", "Carga Errores SINAVID");
		CatalogoCarga.put("CNOMMT4", "Carga Nomina META4");
		CatalogoCarga.put("CTUR", "Carga Turnos");
		CatalogoCarga.put("CHOR", "Carga Horarios");
		model.addAttribute("titulo", "EXCEL " + CatalogoCarga.get(TIPOCARGA));
		model.addAttribute("TIPOCARGA", TIPOCARGA);
		model.addAttribute("quin", "");
		model.addAttribute("lquincenas", lquincenas);
		model.addAttribute("CatalogoCarga", CatalogoCarga);
		model.addAttribute("quinCatSelectPost", quinCatSelectPost);
		model.addAttribute("fechaExt", new Date());
		return "empleados/AddEmpleados";
	}

	@PostMapping("empleados/addXLSX")
	public String addEmpleadosXLSX(Model modelo, RedirectAttributes flash, SessionStatus status,
			@RequestParam("fileXLS") MultipartFile fileXLS, @RequestParam("TIPOCARGA") String TIPOCARGA,
			@RequestParam("CatalogoCarga") String CatalogoCarga,
			@RequestParam(required = false) String quinCatSelectPost,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaExt) {
		try {
			if (TIPOCARGA.equals("CPRE") || TIPOCARGA.equals("CNOM")) {
				if (this.quincenaJPA.countByQuinCat_IdQNA(quinCatSelectPost) > 0)
					throw new Exception("Quincena " + quinCatSelectPost + " ya cargada");
			}
			File filewrite = new File(fileXLS.getOriginalFilename());
			try (FileOutputStream fos = new FileOutputStream(filewrite)) {
				fos.write(fileXLS.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
			this.thread.run(filewrite, TIPOCARGA, quinCatSelectPost, fechaExt);
			modelo.addAttribute("success", "Archivo cargado Satisfactoriamente se prosesaran en segundo plano");
			status.setComplete();
			flash.addFlashAttribute("success", "Archivo procesado con éxito.");

		} catch (Exception err) {
			err.printStackTrace();
			modelo.addAttribute("error",
					err.getMessage().length() > 50 ? err.getMessage().substring(0, 50) : err.getMessage());
		}
		List<String> lquincenas = quincenasCatJPA.findByAllIdQNA();
		modelo.addAttribute("TIPOCARGA", TIPOCARGA);
		modelo.addAttribute("lquincenas", lquincenas);
		modelo.addAttribute("quinCatSelectPost", quinCatSelectPost);
		return "empleados/AddEmpleados";
	}
}
