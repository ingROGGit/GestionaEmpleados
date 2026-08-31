package com.gestion.empleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.entity.ServiciosEntity;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;

@Service
public class ServiciosServiceImpl implements ServiciosService{
	@Autowired
	private ServiciosRepositoryJPA serviciosJPA;
	@Override
	@Transactional(readOnly=true)
	public Page<ServiciosEntity> findAll(Pageable pageable) {
		return serviciosJPA.findAll(pageable);
	}

}
