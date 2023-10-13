package com.gestion.empleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.entity.Empleado;
import com.gestion.empleados.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoRepository empleadoRepository;
	@Override
	@Transactional(readOnly=true)
	public List<Empleado> findAll() {
		// TODO Auto-generated method stub
//		return (List<Empleado>)empleadoRepository.findAll();
		return ((EmpleadoServiceImpl) empleadoRepository).findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Empleado> findAll(Pageable pageable) {
		
		return empleadoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		((EmpleadoServiceImpl) empleadoRepository).save(empleado);
	}

	@Override
	@Transactional(readOnly=true)
	public Empleado findOne(Long id) {
		return ((EmpleadoServiceImpl) empleadoRepository).findOne(id);
	}

	@Override
	public void delete(Long id) {
		((EmpleadoServiceImpl) empleadoRepository).delete(id);
	}
}
