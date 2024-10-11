package com.gestion.empleados.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entity.UsuariosEntity;

public interface UsuariosEntityService {
//	@Secured("ROLE_ADMIN")
	public List<UsuariosEntity> findAll();
//	@Secured("ROLE_ADMIN")
	public Page<UsuariosEntity> findAll(Pageable pageable);

	public void save(UsuariosEntity empleado);

	public UsuariosEntity findOne(Long id);

	public void delete(Long id);

}
