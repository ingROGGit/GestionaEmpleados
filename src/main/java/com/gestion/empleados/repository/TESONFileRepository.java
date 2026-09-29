package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.TESONFilesEntity;


public interface TESONFileRepository  extends JpaRepository<TESONFilesEntity, Serializable>{
	public TESONFilesEntity findById(Long id);
	public Page<TESONFilesEntity> findAll(Pageable pageRequest);
}
