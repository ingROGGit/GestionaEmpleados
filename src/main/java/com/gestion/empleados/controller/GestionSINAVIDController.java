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
import com.gestion.empleados.entity.BancosEntity;
import com.gestion.empleados.entity.CatCPJALEntity;
import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.DomiciliosEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.SINAVIDEntity;
import com.gestion.empleados.repository.AltaSINAVIDFileRepository;
import com.gestion.empleados.repository.CatCPJALRepositoryJPA;
import com.gestion.empleados.repository.DomicilioRepositoryJPA;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
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

	@GetMapping({ "/GestionSINAVID/ListaSinSINAVID" })
	public String contacto(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 300);
		Page<Empleados> empleados = this.empleadosJPA.findByDomiciliosIsNullAndSinavidEmIsNull(pageRequest);
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
		Page<Empleados> empleados = empleadosJPA.findByDomiciliosIsNotNullAndSinavidEmIsNull(pageRequest);
		PageRender<Empleados> pageRender = new PageRender<>("/cuentasBancarias/ListaEmpSinCuenta", empleados);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = quincenasCatJPA.findQNAACT(fechaSqlHoy);
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Alta Cuentas");
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
		List<Empleados> lEmpleadosCuentas = this.empleadosJPA.findByDomiciliosIsNotNullAndSinavidEmIsNull();
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
					SINAVIDEntity sinavid = SINAVIDEntity.builder().estatus("VALIDACION").alta(alta)
							.fechaRegistro(new Date()).sinavidEm(emp).build();
					this.sinavidJPA.save(sinavid);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Error writing file to response", e);
				
			}
		}
		}
		catch(Exception err) {
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
		model.addAttribute("titulo", "Empleados sin Cuenta");
		model.addAttribute("registros", lFileCuentas.getTotalElements());
		return "GestionSINAVID/ListaFilesAltas";
	}

	@GetMapping("/GestionSINAVID/download/{id}")
	public void download(HttpServletResponse respons, @PathVariable(value = "id") Long id)
			throws DocumentException, IOException {
		AltaSINAVIDFilesEntity fileCuenta = this.altaSINAVIDJPA.findById(id);
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
}
