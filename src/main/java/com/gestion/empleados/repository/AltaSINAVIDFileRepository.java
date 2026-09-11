package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.AltaSINAVIDFilesEntity;


public interface AltaSINAVIDFileRepository  extends JpaRepository<AltaSINAVIDFilesEntity, Serializable>{
	public AltaSINAVIDFilesEntity findById(Long id);
}
