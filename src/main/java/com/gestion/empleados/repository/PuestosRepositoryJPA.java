package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.gestion.empleados.entity.PuestosEntity;


public interface PuestosRepositoryJPA extends JpaRepository<PuestosEntity, Serializable>{
	public PuestosEntity findByPuesto(String puesto);
	@Query("select p.puesto from PuestosEntity p")
	public List<String> findByAllPuesto();
}
