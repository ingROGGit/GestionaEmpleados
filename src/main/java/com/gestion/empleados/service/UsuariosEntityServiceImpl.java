package com.gestion.empleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.UsuariosEntityRepositoryJPA;

@Service
public class UsuariosEntityServiceImpl implements UsuariosEntityService{
	
	@Autowired
	private UsuariosEntityRepositoryJPA usuariosEntityRepositoryJPA;

	@Override
	@Transactional(readOnly=true)
	public List<UsuariosEntity> findAll() {
		return usuariosEntityRepositoryJPA.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<UsuariosEntity> findAll(Pageable pageable) {
		return usuariosEntityRepositoryJPA.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(UsuariosEntity empleado) {
		usuariosEntityRepositoryJPA.save(empleado);
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(readOnly=true)
	public UsuariosEntity findOne(Long id) {
		return usuariosEntityRepositoryJPA.getOne(id);
	}

	@Override
	public void delete(Long id) {
		usuariosEntityRepositoryJPA.deleteById(id);
	}

}
