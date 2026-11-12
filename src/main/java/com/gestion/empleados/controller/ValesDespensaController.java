package com.gestion.empleados.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestion.empleados.entity.ValesDespensaEntity;
import com.gestion.empleados.repository.ValesDespensaRepositoryJPA;
import com.gestion.empleados.utils.PageRender;

import jakarta.validation.Valid;

@Controller
public class ValesDespensaController {
	@Autowired
	private ValesDespensaRepositoryJPA valesDespensaJPA;
	@GetMapping("ValesDespensa/entregaTarValesDespensa")
	public String listarValesDespensa(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5000);
		Page<ValesDespensaEntity> lvalesDespensa = this.valesDespensaJPA.findAll(pageRequest);
		PageRender<ValesDespensaEntity> pageRender = new PageRender<>("/ValesDespensa/entregaTarValesDespensa", lvalesDespensa);
		model.addAttribute("titulo", "Listado Vales de Despensa");
		model.addAttribute("totalVales", this.valesDespensaJPA.count());
		model.addAttribute("valesEntregados", this.valesDespensaJPA.countByEntregadoIsTrue());
		model.addAttribute("lvalesDespensa", lvalesDespensa);
		model.addAttribute("page", pageRender);
		return "valesDespensa/listarValesDespensa";
	}
	@GetMapping("ValesDespensa/entregaTarjeta/{id}")
	public String entregaTarjeta(@PathVariable(value = "id") UUID id,
			Map<String, Object> modelo, RedirectAttributes flash) {
		Optional<ValesDespensaEntity> valeDespensa=this.valesDespensaJPA.findById(id);
		modelo.put("valeDespensa", valeDespensa.get());
		modelo.put("titulo", "Entrega Tarjeta de Vales");
		return "ValesDespensa/entregaTarjetaModal";
	}
	@PostMapping("ValesDespensa/entregarTarjeta")
	public String entregarTarjeta(@Valid ValesDespensaEntity valeAdd,
			Map<String, Object> modelo, RedirectAttributes flash) {
		LocalDateTime hoy = LocalDateTime.now();
		ValesDespensaEntity valeDespensa=this.valesDespensaJPA.getById(valeAdd.getId());
		System.out.println("Se validan:"+valeAdd.getFolio()+"!="+valeAdd.getScaner());
		if(!valeAdd.getFolio().trim().equals(valeAdd.getScaner().trim())) {
			flash.addFlashAttribute("error", "La tarjeta no conincide "+valeAdd.getFolio()+" con:"+valeAdd.getScaner());
			return "redirect:/ValesDespensa/entregaTarValesDespensa";
		}
		valeDespensa.setFechaEntrega(hoy);
		valeDespensa.setScaner(valeAdd.getScaner());
		valeDespensa.setEntregado(true);
		this.valesDespensaJPA.save(valeDespensa);
		flash.addFlashAttribute("success", "Tarjeta entregada al empleado: "+valeDespensa.getEmpleado().getId()+" "+valeDespensa.getEmpleado().getNombre());
		return "redirect:/ValesDespensa/entregaTarValesDespensa";
	}
	@GetMapping("ValesDespensa/verDetallesVales/{id}")
	public String DetalleTarjetaModal(@PathVariable(value = "id") UUID id,
			Map<String, Object> modelo, RedirectAttributes flash) {
		Optional<ValesDespensaEntity> valeDespensa=this.valesDespensaJPA.findById(id);
		modelo.put("valeDespensa", valeDespensa.get());
		modelo.put("titulo", "Detalles Tarjeta de Vales");
		return "ValesDespensa/DetalleTarjetaModal";
	}
}
