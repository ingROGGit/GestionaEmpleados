package com.gestion.empleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gestion.empleados.entity.DeduccionesEntity;
import com.gestion.empleados.repository.DeduccionesRepositoryJPA;

@Service
public class DeduccionesServiceImpl implements DeduccionesService{
@Autowired
private DeduccionesRepositoryJPA deduccionesJPA;

@Override
public Page<DeduccionesEntity> findAll(Pageable pageable) {
	return deduccionesJPA.findAll(pageable);
}
}
