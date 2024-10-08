package com.gestion.empleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.JPA.Empleados;
import com.gestion.empleados.entity.Empleado;
import com.gestion.empleados.repository.EmpleadoRepositoryJPA;


@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoRepositoryJPA empleadoRepository;
	@Override
	@Transactional(readOnly=true)
	public List<Empleados> findAll() {
		// TODO Auto-generated method stub
//		return (List<Empleado>)empleadoRepository.findAll();
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

	@Override
	public void save(Empleado empleado) {
		// TODO Auto-generated method stub
		
	}
}
