package com.gestion.empleados.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entity.PuestosEntity;


public interface PuestosService {
	public Page<PuestosEntity> findAll(Pageable pageable);
}
