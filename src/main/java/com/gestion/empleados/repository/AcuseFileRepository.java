package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.AcuseFilesEntity;


public interface AcuseFileRepository extends JpaRepository<AcuseFilesEntity, Serializable>{
	public AcuseFilesEntity findById(UUID idFile);
	public AcuseFilesEntity findByFileName(String fileName);
	public List<AcuseFilesEntity> findByEmpleado_Id(Long idEmpleado);
}
