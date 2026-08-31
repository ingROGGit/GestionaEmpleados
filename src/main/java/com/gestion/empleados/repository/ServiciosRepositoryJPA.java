package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestion.empleados.entity.ServiciosEntity;

public interface ServiciosRepositoryJPA extends JpaRepository<ServiciosEntity, Serializable>{
	public ServiciosEntity findByServicio(String servicio);
	@Query("select s.servicio from ServiciosEntity s")
	public List<String> findByAllServicio();
}
