package com.gestion.empleados.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;

import com.gestion.empleados.entity.Empleado;

public interface EmpleadoService {
//	@Secured("ROLE_ADMIN")
	public List<Empleado> findAll();
//	@Secured("ROLE_ADMIN")
	public Page<Empleado> findAll(Pageable pageable);

	public void save(Empleado empleado);

	public Empleado findOne(Long id);

	public void delete(Long id);
}
