package com.gestion.empleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gestion.empleados.entity.PersepcionesEntity;
import com.gestion.empleados.repository.PersepcionesRepositoryJPA;

@Service
public class PersepcionesServiceImpl implements PersepcionesService{
	@Autowired
	private PersepcionesRepositoryJPA persepcionesJPA;
	@Override
	public Page<PersepcionesEntity> findAll(Pageable pageable) {
		return persepcionesJPA.findAll(pageable);
	}

}
