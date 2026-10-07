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
import com.gestion.empleados.entity.AltaSINAVIDFilesEntity;
import com.gestion.empleados.entity.BajasSINAVIDFilesEntity;
import com.gestion.empleados.entity.BancosEntity;
import com.gestion.empleados.entity.CatCPJALEntity;
import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.DomiciliosEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.ExtractoSINAVIDFilesEntity;
import com.gestion.empleados.entity.ModSalSINAVIDFilesEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.SINAVIDEntity;
import com.gestion.empleados.repository.AltaSINAVIDFileRepository;
import com.gestion.empleados.repository.BajasSINAVIDFileRepository;
import com.gestion.empleados.repository.CatCPJALRepositoryJPA;
import com.gestion.empleados.repository.DomicilioRepositoryJPA;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.ExtractoSINAVIDFilesRepository;
import com.gestion.empleados.repository.ModSalSINAVIDFileRepository;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.SINAVIDRepositoryJPA;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.reports.ExportCuentasBanco;
import com.gestion.empleados.utils.reports.ExporterTXTSINAVID;
import com.lowagie.text.DocumentException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class GestionSINAVIDController {
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private CatCPJALRepositoryJPA cpjJPA;
	@Autowired
	private DomicilioRepositoryJPA domiJPA;
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private AltaSINAVIDFileRepository altaSINAVIDJPA;
	@Autowired
	private SINAVIDRepositoryJPA sinavidJPA;
	@Autowired
	private ExtractoSINAVIDFilesRepository extractoJPA;
	@Autowired
	private ModSalSINAVIDFileRepository modSalJPA;
	@Autowired
	private BajasSINAVIDFileRepository bajasRepJPA;
	@GetMapping({ "/GestionSINAVID/ListaSinSINAVID" })
	public String contacto(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 300);
		Page<Empleados> empleados = this.empleadosJPA.findByDomiciliosIsNullAndSinavidIsNull(pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/GestionSINAVID/ListaSinSINAVID", empleados);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		File file = new File("SINAVID" + fa + ".xlsx");
		System.out.println(file.getAbsolutePath());
		boolean fileExDownload = false;
		if (file.exists() && file.canWrite()) {
			fileExDownload = true;
		}
		model.addAttribute("fileExDownload", fileExDownload);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Empleados sin SINAVID");
		model.addAttribute("registros", empleados.getTotalElements());
		return "GestionSINAVID/ListaSinSINAVID";
	}

	@GetMapping("/GestionSINAVID/formDomi/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id, @RequestParam(required = false) String addNew,
			Map<String, Object> modelo, RedirectAttributes flash) {
		Empleados emp = this.empleadosJPA.findById(id);
		DomiciliosEntity domi = this.domiJPA.findByDomiEm_Id(id);
		if (domi == null) {
			domi = new DomiciliosEntity();
			domi.setDomiEm(emp);
		}
		List<CatCPJALEntity> LCPJ = this.cpjJPA.findAll();
		modelo.put("domi", domi);
		modelo.put("LCPJ", LCPJ);
		modelo.put("titulo", "Registrar Domicilio");
		return "GestionSINAVID/formDomiModal";
	}

	@PostMapping("GestionSINAVID/formDomi")
	public String guardaEmpleado(@Valid DomiciliosEntity domi, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Domicilio");
			return "GestionSINAVID/formDomi";
		}
		String mensaje = "Registrado con Exito";
		this.domiJPA.save(domi);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/GestionSINAVID/ListaSinSINAVID";
	}

	@GetMapping("/GestionSINAVID/UPSINAVID")
	public String UPSINAVID(@RequestParam(name = "page", defaultValue = "0") int page, Model model)
			throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<Empleados> empleados = this.empleadosJPA.findByDomiciliosIsNotNullAndSinavidIsNullOrSinavid_EstatusAndSinavid_NumISSSTEIsNull(pageRequest,"CORREGIDO");
		PageRender<Empleados> pageRender = new PageRender<>("/cuentasBancarias/ListaEmpSinCuenta", empleados);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = quincenasCatJPA.findQNAACT(fechaSqlHoy);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Altas SINAVID");
		model.addAttribute("empleados", empleados);
		model.addAttribute("alta", fa);
		model.addAttribute("quinCatSelect", quincenasCat);
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("registros", empleados.getTotalElements());
		return "GestionSINAVID/UPSINAVID";
	}

	@PostMapping("/GestionSINAVID/WriteFile")
	public void FileExcel(HttpServletResponse respons, @RequestParam("alta") String alta,
			@RequestParam("quinCatSelect") String quinCatSelect) throws Exception {
		try {
			File file = new File(alta + ".txt");
			List<Empleados> lEmpleadosCuentas = this.empleadosJPA.findByDomiciliosIsNotNullAndSinavidIsNullOrSinavid_EstatusAndSinavid_NumISSSTEIsNull("CORREGIDO");
			ExporterTXTSINAVID altaSINAVID = new ExporterTXTSINAVID(file.getName(), lEmpleadosCuentas, quinCatSelect);
			altaSINAVID.runAltaCuentas();
			if (file.exists() && file.canWrite()) {
				AltaSINAVIDFilesEntity acfile;
				respons.setContentType("application/octet-stream");
				String cabecera = "Content-Disposition";
				String valor = "attachment; filename=" + file.getName();
				respons.setHeader(cabecera, valor);
				try (InputStream inputStream = new FileInputStream(file);
						ServletOutputStream outputStream = respons.getOutputStream()) {
					IOUtils.copy(inputStream, outputStream);
					respons.flushBuffer();
					inputStream.close();
					acfile = AltaSINAVIDFilesEntity.builder().alta(alta).fechaAlta(new Date())
							.fileAlta(Files.readAllBytes(file.toPath())).build();
					this.altaSINAVIDJPA.save(acfile);
					file.delete();
					for (Empleados emp : lEmpleadosCuentas) {
						SINAVIDEntity sinavid=null;
						if(emp.getSinavid()!=null) {
							sinavid=emp.getSinavid();
							sinavid.setEstatus("VALIDACION");
						}
						else {
							sinavid = SINAVIDEntity.builder().estatus("VALIDACION").alta(alta)
									.fechaRegistro(new Date()).empleado(emp).build();
						}
						this.sinavidJPA.save(sinavid);
					}
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("Error writing file to response", e);

				}
			}
		} catch (Exception err) {
			throw new Exception(err);
		}
	}

	@GetMapping({ "/GestionSINAVID/ListaAltas" })
	public String ListaFilesCuenta(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<AltaSINAVIDFilesEntity> lFileCuentas = this.altaSINAVIDJPA.findAll(pageRequest);
		PageRender<AltaSINAVIDFilesEntity> pageRender = new PageRender<>("/cuentasBancarias/ListaFilesCuenta",
				lFileCuentas);
		model.addAttribute("files", lFileCuentas);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Listado de Altas");
		model.addAttribute("registros", lFileCuentas.getTotalElements());
		return "GestionSINAVID/ListaFilesAltas";
	}

	@GetMapping("/GestionSINAVID/download/{id}")
	public void download(HttpServletResponse respons, @PathVariable(value = "id") Long id)
			throws DocumentException, IOException {
		AltaSINAVIDFilesEntity fileCuenta = this.altaSINAVIDJPA.findById(id);
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=" + fileCuenta.getAlta() + ".txt";
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

	@GetMapping({ "/GestionSINAVID/ListaExtractos" })
	public String ListaFilesExtracto(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<ExtractoSINAVIDFilesEntity> lFileCuentas = this.extractoJPA.findAll(pageRequest);
		PageRender<ExtractoSINAVIDFilesEntity> pageRender = new PageRender<>("/cuentasBancarias/ListaFilesCuenta",
				lFileCuentas);
		model.addAttribute("files", lFileCuentas);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista Extractos");
		model.addAttribute("registros", lFileCuentas.getTotalElements());
		return "GestionSINAVID/ListaFilesExtracto";
	}

	@GetMapping("/GestionSINAVID/downloadExtractos/{id}")
	public void downloadExtracto(HttpServletResponse respons, @PathVariable(value = "id") Long id)
			throws DocumentException, IOException {
		ExtractoSINAVIDFilesEntity fileCuenta = this.extractoJPA.findById(id);
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=" + fileCuenta.getFileName();
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
	@GetMapping("/empleados/bajaSINAVID/{id}")
	public String marcarbaja(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Empleados emp=this.empleadosJPA.getById(id);
			SINAVIDEntity sinavid=emp.getSinavid();
			sinavid.setEstatus("PARA BAJA");
			this.sinavidJPA.save(sinavid);
			flash.addFlashAttribute("error", "Empleado para Baja SINAVID "+emp.getId()+" "+emp.getNombreCompleto());
		}
		return "redirect:/empleados/listarEmpleados";
	}
	@GetMapping("/empleados/bajaDefSINAVID/{id}")
	public String bajaDefinitiva(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Empleados emp=this.empleadosJPA.getById(id);
			SINAVIDEntity sinavid=emp.getSinavid();
			sinavid.setEstatus("BAJA");
			sinavid.setActivo(false);
			this.sinavidJPA.save(sinavid);
			flash.addFlashAttribute("error", "Empleado Baja SINAVID "+emp.getId()+" "+emp.getNombreCompleto());
		}
		return "redirect:/empleados/listarEmpleados";
	}
	@GetMapping("/empleados/altaSINAVID/{id}")
	public String marcaAltaSINAVID(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Empleados emp=this.empleadosJPA.getById(id);
			SINAVIDEntity sinavid=emp.getSinavid();
			sinavid.setEstatus("ALTA");
			sinavid.setActivo(true);
			if(sinavid.getFechaAlta()==null)
				sinavid.setFechaAlta(sinavid.getFechaRegistro()!=null?sinavid.getFechaRegistro():new Date());
			this.sinavidJPA.save(sinavid);
			flash.addFlashAttribute("success", "Empleado Alta SINAVID "+emp.getId()+" "+emp.getNombreCompleto());
		}
		return "redirect:/empleados/listarEmpleados";
	}
	@GetMapping("/GestionSINAVID/UPBajaSINAVID")
	public String UPBajaSINAVID(@RequestParam(name = "page", defaultValue = "0") int page, Model model)
			throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<Empleados> empleados = this.empleadosJPA.findBySinavid_Estatus(pageRequest, "PARA BAJA");
		PageRender<Empleados> pageRender = new PageRender<>("/cuentasBancarias/UPBajaSINAVID", empleados);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Bajas SINAVID");
		model.addAttribute("empleados", empleados);
		model.addAttribute("baja", fa);
		model.addAttribute("registros", empleados.getTotalElements());
		return "GestionSINAVID/UPBajaSINAVID";
	}
	@GetMapping("/GestionSINAVID/formBajaSINAVID/{id}")
	public String upBajaSINAVID(@PathVariable(value = "id") Long id,@RequestParam(required = false) String addNew, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleados emp=this.empleadosJPA.findById(id);
		SINAVIDEntity sinavid=emp.getSinavid();
		modelo.put("sinavid", sinavid);
		modelo.put("titulo", "Bajas SINAVID");
		return "GestionSINAVID/formBajaSINAVIDModal";
	}
	@PostMapping("GestionSINAVID/formBajaSINAVID")
	public String upBajaSINAVID(@Valid SINAVIDEntity sinavid, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		modelo.addAttribute("titulo", "Registro de Cuenta");
		if (result.hasErrors()) {
			
			return "GestionSINAVID/formBajaSINAVIDModal";
		}
		SINAVIDEntity sinavidDB=this.sinavidJPA.findById(sinavid.getId());
		sinavidDB.setFechaBaja(sinavid.getFechaBaja());
		sinavidDB.setIdBaja(sinavid.getIdBaja());
		this.sinavidJPA.save(sinavidDB);
		String mensaje = "Datos registrados";
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/GestionSINAVID/UPBajaSINAVID";
	}
	@PostMapping("/GestionSINAVID/WriteBajas")
	public void WriteBajas(HttpServletResponse respons, @RequestParam("baja") String baja) throws Exception {
		try {
			File file = new File(baja + ".txt");
			List<Empleados> lEmpleadosCuentas =this.empleadosJPA.findBySinavid_EstatusAndSinavid_FechaBajaIsNotNull("PARA BAJA");
			ExporterTXTSINAVID altaSINAVID = new ExporterTXTSINAVID(file.getName(), lEmpleadosCuentas, "");
			altaSINAVID.runBaja();
			if (file.exists() && file.canWrite()) {
				BajasSINAVIDFilesEntity acfile;
				respons.setContentType("application/octet-stream");
				String cabecera = "Content-Disposition";
				String valor = "attachment; filename=" + file.getName();
				respons.setHeader(cabecera, valor);
				try (InputStream inputStream = new FileInputStream(file);
						ServletOutputStream outputStream = respons.getOutputStream()) {
					IOUtils.copy(inputStream, outputStream);
					respons.flushBuffer();
					inputStream.close();
					acfile = BajasSINAVIDFilesEntity.builder().alta(baja).fechaAlta(new Date())
							.fileAlta(Files.readAllBytes(file.toPath())).build();
					this.bajasRepJPA.save(acfile);
					file.delete();
					for (Empleados emp : lEmpleadosCuentas) {
						SINAVIDEntity sinavid = emp.getSinavid();
						sinavid.setEstatus("VALIDACION");
						this.sinavidJPA.save(sinavid);
					}
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException("Error writing file to response", e);

				}
			}
		} catch (Exception err) {
			throw new Exception(err);
		}
	}
	@GetMapping("/GestionSINAVID/UPMODSINAVID")
	public String UPMODSINAVID(@RequestParam(name = "page", defaultValue = "0") int page, Model model)
			throws DocumentException, IOException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = quincenasCatJPA.findQNAACT(fechaSqlHoy);
		List<Empleados> empleados = this.empleadosJPA.findByModificacionSalario(quincenasCat,"MODIFICACION S");
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", null);
		model.addAttribute("titulo", "Modificacion de Salarios");
		model.addAttribute("empleados", empleados);
		model.addAttribute("alta", fa);
		model.addAttribute("quinCatSelect", quincenasCat);
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("registros", empleados.size());
		return "GestionSINAVID/UPMODSINAVID";
	}

	@PostMapping("/GestionSINAVID/WritMODSAL")
	public Object WritMODSAL(Model model,HttpServletResponse respons, @RequestParam("alta") String alta,
			@RequestParam("quinCatSelect") String quinCatSelect, @RequestParam(name = "accion") String accion)
			throws Exception {
		try {
			if ("layout1".equals(accion)) {
				List<Empleados> empleados = this.empleadosJPA.findByModificacionSalario(quinCatSelect,"MODIFICACION S");
				List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
				model.addAttribute("empleados", empleados);
				model.addAttribute("page", null);
				model.addAttribute("titulo", "Modificacion de Salarios");
				model.addAttribute("empleados", empleados);
				model.addAttribute("alta", alta);
				model.addAttribute("quinCatSelect", quinCatSelect);
				model.addAttribute("LquincenasCat", LquincenasCat);
				model.addAttribute("registros", empleados.size());
				return "GestionSINAVID/UPMODSINAVID";
			}
			if ("layout2".equals(accion)) {
				File file = new File(alta + ".txt");
				QuincenaCatEntity quincenaCat=this.quincenasCatJPA.findByIdQNA(quinCatSelect);
				List<Empleados> lEmpleadosCuentas =this.empleadosJPA.findByModificacionSalario(quinCatSelect,"MODIFICACION S");
				ExporterTXTSINAVID altaSINAVID = new ExporterTXTSINAVID(file.getName(), lEmpleadosCuentas,
						quinCatSelect);
				altaSINAVID.runModSalario(quincenaCat.getFechaInicio());
				if (file.exists() && file.canWrite()) {
					ModSalSINAVIDFilesEntity acfile;
					respons.setContentType("application/octet-stream");
					String cabecera = "Content-Disposition";
					String valor = "attachment; filename=" + file.getName();
					respons.setHeader(cabecera, valor);
					try (InputStream inputStream = new FileInputStream(file);
							ServletOutputStream outputStream = respons.getOutputStream()) {
						IOUtils.copy(inputStream, outputStream);
						respons.flushBuffer();
						inputStream.close();
						acfile = ModSalSINAVIDFilesEntity.builder().alta(alta).fechaAlta(new Date())
								.fileAlta(Files.readAllBytes(file.toPath())).build();
						this.modSalJPA.save(acfile);
						file.delete();
						for (Empleados emp : lEmpleadosCuentas) {
							SINAVIDEntity sinavid = emp.getSinavid();
							sinavid.setEstatus("MODIFICACION S");
							this.sinavidJPA.save(sinavid);
						}
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException("Error writing file to response", e);

					}
				}
				return null;
			}
		} catch (Exception err) {
			throw new Exception(err);
		}
		return "redirect:/GestionSINAVID/inicio"; 
	}
	@GetMapping({ "/GestionSINAVID/ListaFilesBajas" })
	public String ListaFilesBajas(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<BajasSINAVIDFilesEntity> lFiles = this.bajasRepJPA.findAll(pageRequest);
		PageRender<BajasSINAVIDFilesEntity> pageRender = new PageRender<>("/GestionSINAVID/ListaFilesBajas",lFiles);
		model.addAttribute("files", lFiles);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista de Bajas SINAVID");
		model.addAttribute("registros", lFiles.getTotalElements());
		return "GestionSINAVID/ListaFilesBajas";
	}

	@GetMapping("/GestionSINAVID/downloadFileBaja/{id}")
	public void downloadFilesBajas(HttpServletResponse respons, @PathVariable(value = "id") Long id)
			throws DocumentException, IOException {
		BajasSINAVIDFilesEntity fileCuenta = this.bajasRepJPA.findById(id);
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=" + fileCuenta.getAlta()+".txt";
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
	@GetMapping({ "/GestionSINAVID/ListaFilesModSalarios" })
	public String ListaFilesModSalarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<ModSalSINAVIDFilesEntity> lFiles = this.modSalJPA.findAll(pageRequest);
		PageRender<ModSalSINAVIDFilesEntity> pageRender = new PageRender<>("/GestionSINAVID/ListaFilesModSalario",lFiles);
		model.addAttribute("files", lFiles);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista Modificacion de Salarios");
		model.addAttribute("registros", lFiles.getTotalElements());
		return "GestionSINAVID/ListaFilesModSalario";
	}

	@GetMapping("/GestionSINAVID/downloadFileModSalario/{id}")
	public void downloadFilesModSalarios(HttpServletResponse respons, @PathVariable(value = "id") Long id)
			throws DocumentException, IOException {
		ModSalSINAVIDFilesEntity fileCuenta = this.modSalJPA.getById(id);
		respons.setContentType("application/octet-stream");
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=" + fileCuenta.getAlta()+".txt";
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
	@GetMapping({ "/GestionSINAVID/ListaErrorSINAVID" })
	public String listaErroresSINAVID(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 300);
		Page<Empleados> empleados = this.empleadosJPA.findBySinavid_EstatusOrSinavid_ErrorIsNotNull(pageRequest,"ERROR");
		PageRender<Empleados> pageRender = new PageRender<>("/GestionSINAVID/ListaErrorSINAVID", empleados);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Errores en SINAVID");
		model.addAttribute("registros", empleados.getTotalElements());
		return "GestionSINAVID/ListaErrorSINAVID";
	}
	
	@GetMapping("/GestionSINAVID/eliminaError/{id}")
	public String cleanError(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Empleados emp=this.empleadosJPA.getById(id);
			SINAVIDEntity sinavid=emp.getSinavid();
			sinavid.setError(null);
			sinavid.setActivo(true);
			this.sinavidJPA.save(sinavid);
			flash.addFlashAttribute("success", "Empleado se elimina error SINAVID "+emp.getId()+" "+emp.getNombreCompleto());
		}
		return "redirect:/GestionSINAVID/ListaErrorSINAVID";
	}
	
	@GetMapping("/GestionSINAVID/addFolio/{id}")
	public String addFolio(@PathVariable(value = "id") Long id, @RequestParam(required = false) String addNew,
				Map<String, Object> modelo, RedirectAttributes flash) {
			AltaSINAVIDFilesEntity file= this.altaSINAVIDJPA.findById(id);
			modelo.put("file", file);
			modelo.put("titulo", "Registrar Filio de SINAVID");
			return "GestionSINAVID/formAddFolio";
		}
	
	@PostMapping("GestionSINAVID/addFolioSINAVID")
	public String addFolioSINAVID(@Valid AltaSINAVIDFilesEntity file, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de Cuenta");
			return "GestionSINAVID/formAddFolio";
		}
		AltaSINAVIDFilesEntity fileDB=this.altaSINAVIDJPA.findById(file.getId());
		fileDB.setFolio(file.getFolio());
		this.altaSINAVIDJPA.save(fileDB);
		String mensaje = "Folio Registrado con Exito";
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/GestionSINAVID/ListaAltas";
	}
}
