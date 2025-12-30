package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.ReglasDiasEntity;

public interface ReglasDiasRepository extends JpaRepository<ReglasDiasEntity, Serializable>{
	public ReglasDiasEntity findById(Long id);
	public void deleteById(Long id);
}
