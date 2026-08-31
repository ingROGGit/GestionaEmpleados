package com.gestion.empleados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entity.DeduccionesEntity;



public interface DeduccionesService {
	public Page<DeduccionesEntity> findAll(Pageable pageable);
}
