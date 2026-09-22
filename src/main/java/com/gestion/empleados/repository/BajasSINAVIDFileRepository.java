package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.empleados.entity.BajasSINAVIDFilesEntity;


public interface BajasSINAVIDFileRepository  extends JpaRepository<BajasSINAVIDFilesEntity, Serializable>{
	public BajasSINAVIDFilesEntity findById(Long id);
}
