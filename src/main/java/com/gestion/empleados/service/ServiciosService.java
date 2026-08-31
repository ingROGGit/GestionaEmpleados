package com.gestion.empleados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entity.ServiciosEntity;


public interface ServiciosService {
	public Page<ServiciosEntity> findAll(Pageable pageable);
}
