package com.gestion.empleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;


@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadosRepositoryJPA empleadoRepository;
	@Override
	@Transactional(readOnly=true)
	public List<Empleados> findAll() {
		return empleadoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Empleados> findAll(Pageable pageable) {
		
		return empleadoRepository.findAll(pageable);
	}

	@Transactional
	public void save(Empleados empleado) {
		empleadoRepository.save(empleado);
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(readOnly=true)
	public Empleados findOne(Long id) {
		return empleadoRepository.getOne(id);
	}

	@Override
	public void delete(Long id) {
		empleadoRepository.deleteById(id);
	}

}
