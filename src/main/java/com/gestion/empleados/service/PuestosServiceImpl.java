package com.gestion.empleados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.repository.PuestosRepositoryJPA;

@Service
public class PuestosServiceImpl implements PuestosService {
	@Autowired
	private PuestosRepositoryJPA puestosJPA;
	@Override
	@Transactional(readOnly=true)
	public Page<PuestosEntity> findAll(Pageable pageable) {
		return puestosJPA.findAll(pageable);
	}

}
