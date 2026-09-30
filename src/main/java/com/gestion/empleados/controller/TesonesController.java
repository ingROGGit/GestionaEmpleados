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
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.JefesEntity;
import com.gestion.empleados.entity.MotivoTESONEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.SINAVIDEntity;
import com.gestion.empleados.entity.TESONESEntity;
import com.gestion.empleados.entity.TESONFilesEntity;
import com.gestion.empleados.entity.TipoNomTESONEntity;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.MotivoTESONRepository;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.TESONFileRepository;
import com.gestion.empleados.repository.TESONRepository;
import com.gestion.empleados.repository.TipoNomTESONRepository;
import com.gestion.empleados.repository.jefesRepositoryJPA;
import com.gestion.empleados.utils.PageRender;
import com.gestion.empleados.utils.reports.ExportTESONES;
import com.lowagie.text.DocumentException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class TesonesController {
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private TESONFileRepository tesonFileJPA;
	@Autowired
	private TESONRepository tesonJPA;
	@Autowired
	private MotivoTESONRepository motivoTJPA;
	@Autowired
	private TipoNomTESONRepository tipoNomJPA;
	@Autowired
	private jefesRepositoryJPA jefesJPA;
	@Autowired
	private QuincenaRepositoryJPA quincenasJPA;
	@GetMapping("/tesones/UPTESON")
	public String UPTESON(@RequestParam(name = "page", defaultValue = "0") int page, Model model)
			throws DocumentException, IOException {
		Pageable pageRequest = PageRequest.of(page, 5000);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fa = df.format(new Date());
		java.sql.Date fechaSqlHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
		String quincenasCat = this.quincenasCatJPA.findQNAACT(fechaSqlHoy);
		Page<TESONESEntity> lTesones = this.tesonJPA.findByEstatus("POR GENERAR",pageRequest);
		PageRender<TESONESEntity> pageRender = new PageRender<>("/tesones/UPTESON", lTesones);
		List<String> LquincenasCat = this.quincenasCatJPA.findByAllIdQNA();
		List<TipoNomTESONEntity> ltipoNom=this.tipoNomJPA.findAll();
		List<JefesEntity> ljefes=this.jefesJPA.findAll();
		model.addAttribute("lTesones", lTesones);
		model.addAttribute("ltipoNomGen", ltipoNom);
		model.addAttribute("tipoSelect", "");
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Generacion de TESONES");
		model.addAttribute("empleados", lTesones);
		model.addAttribute("nombreT", fa+"-");
		model.addAttribute("quinCatSelect", quincenasCat);
		model.addAttribute("LquincenasCat", LquincenasCat);
		model.addAttribute("registros", lTesones.getTotalElements());
		model.addAttribute("ljefes",ljefes);
		return "tesones/UPTESON";
	}
	
	@GetMapping("/tesones/UPDatos/{id}")
	public String teSONUPDAtos(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		TESONESEntity teson = this.tesonJPA.getById(id);
		List<MotivoTESONEntity> lmotivos=this.motivoTJPA.findAll();
		List<TipoNomTESONEntity> ltipoNom=this.tipoNomJPA.findAll();
		modelo.put("teson", teson);
		modelo.put("lmotivos", lmotivos);
		modelo.put("ltipoNom", ltipoNom);
		modelo.put("titulo", "Registrar TESON");
		return "tesones/formTESONModal";
	}
	@PostMapping("tesones/saveUPDatos")
	public String teSONUPDAtos(@Valid TESONESEntity teson, BindingResult result, Model modelo,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro TESON");
			return "tesones/UPTESON";
		}
		String mensaje ="TESON Actualizado correctamente";
		TESONESEntity tesonDB=this.tesonJPA.findById(teson.getId());
		tesonDB.setTipoNomTESONEntity(teson.getTipoNomTESONEntity());
		tesonDB.setMotivoTESONEntity(teson.getMotivoTESONEntity());
		this.tesonJPA.save(tesonDB);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/tesones/UPTESON";
	}
	@PostMapping("/tesones/UPTESON")
	public void WritMODSAL(Model model,HttpServletResponse respons, @RequestParam("nombreT") String nombreT,
			@RequestParam("quinCatSelect") String quinCatSelect,@RequestParam("tipoSelect") String tipoSelect,@RequestParam("jefeSelect") int jefeSelect)
			throws Exception {
		try {
				File file = new File(nombreT + ".xlsx");
				QuincenaCatEntity quincenaCat=this.quincenasCatJPA.findByIdQNA(quinCatSelect);
				List<TESONESEntity> ltesones=this.tesonJPA.findByEstatusAndTipoNomTESONEntity_Id("POR GENERAR",Long.valueOf(tipoSelect));
				TipoNomTESONEntity tipN=this.tipoNomJPA.getById(Long.valueOf(tipoSelect));
				JefesEntity jefe=this.jefesJPA.getById(jefeSelect);
				List<String> lfolisChequesString=this.quincenasJPA.findByFolisLong(quincenaCat.getIdQNA(),"Cheque");
				List<Long> listaLongCheques = lfolisChequesString.stream()
					    .map(Long::parseLong) // o Long::valueOf
					    .collect(Collectors.toList());
				ExportTESONES theadTeson = new ExportTESONES(file.getName(), ltesones,quincenaCat,tipN.getTipo(),jefe,listaLongCheques);
				theadTeson.run();
				if (file.exists() && file.canWrite()) {
					TESONFilesEntity acfile;
					respons.setContentType("application/octet-stream");
					String cabecera = "Content-Disposition";
					String valor = "attachment; filename=" + file.getName();
					respons.setHeader(cabecera, valor);
					try (InputStream inputStream = new FileInputStream(file);
							ServletOutputStream outputStream = respons.getOutputStream()) {
						IOUtils.copy(inputStream, outputStream);
						respons.flushBuffer();
						inputStream.close();
						acfile = TESONFilesEntity.builder().alta(nombreT).fechaAlta(new Date())
								.fileAlta(Files.readAllBytes(file.toPath())).build();
						this.tesonFileJPA.save(acfile);
						for(TESONESEntity teson:ltesones) {
							teson.setEstatus("GENERADO");
							this.tesonJPA.save(teson);
						}
						file.delete();
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException("Error writing file to response", e);

					}
				}
		} catch (Exception err) {
			throw new Exception(err);
		}
	}
	@GetMapping({ "/tesones/ListaTesones" })
	public String ListaTesones(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 200);
		Page<TESONFilesEntity> lFiles = this.tesonFileJPA.findAll(pageRequest);
		PageRender<TESONFilesEntity> pageRender = new PageRender<>("/tesones/ListaTesones",lFiles);
		model.addAttribute("files", lFiles);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista de TESONES");
		model.addAttribute("registros", lFiles.getTotalElements());
		return "tesones/ListaFilesTesones";
	}

	@GetMapping("/tesones/downloadFileTeson/{id}")
	public void downloadFileTeson(HttpServletResponse respons, @PathVariable(value = "id") Long id)
			throws DocumentException, IOException {
		TESONFilesEntity fileCuenta = this.tesonFileJPA.getById(id);
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
	
	@GetMapping("/tesones/eliminarTESON/{id}")
	public String eliminarTESON(@PathVariable(value = "id") Long id, Model modelo,
			RedirectAttributes flash, SessionStatus status)
			throws DocumentException, IOException {
		TESONESEntity teson=this.tesonJPA.findById(id);
		QuincenasEntity quin=this.quincenasJPA.findById(teson.getQuincena().getId());
		quin.setTeson(false);
		this.quincenasJPA.save(quin);
		this.tesonJPA.delete(teson);
		flash.addFlashAttribute("success", "Empleado Eliminado de TESON"+teson.getEmpleado().getId()+" "+teson.getEmpleado().getNombre());
		return "redirect:/tesones/UPTESON";
	}
}
