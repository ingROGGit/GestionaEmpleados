package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;


public interface QuincenaRepositoryJPA extends JpaRepository<QuincenasEntity, Serializable>{
	public Page<QuincenasEntity> findByQuinCat(QuincenaCatEntity quinCat, Pageable pageable);
	public Page<QuincenasEntity> findByQuinCatAndEmpleadoQN_IdOrEmpleadoQN_NombreCompletoContainingIgnoreCaseOrEmpleadoQN_CurpContainingIgnoreCaseOrEmpleadoQN_RfcContainingIgnoreCase(QuincenaCatEntity quinCat,Long idEmpleado,String nom,String curp,String rfc, Pageable pageable);
	}
