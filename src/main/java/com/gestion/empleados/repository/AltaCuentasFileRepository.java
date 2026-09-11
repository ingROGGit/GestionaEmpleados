package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.AltaCuentasFilesEntity;



public interface AltaCuentasFileRepository  extends JpaRepository<AltaCuentasFilesEntity, Serializable>{
	public AltaCuentasFilesEntity findById(Long id);
}
