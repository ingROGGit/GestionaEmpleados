package com.gestion.empleados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entity.PersepcionesEntity;




public interface PersepcionesService {
	public Page<PersepcionesEntity> findAll(Pageable pageable);
}
