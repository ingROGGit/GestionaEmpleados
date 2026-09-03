package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;
import java.sql.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion.empleados.entity.QuincenaCatEntity;

public interface QuincenasCatRepositoryJPA extends JpaRepository<QuincenaCatEntity, Serializable>{
	public QuincenaCatEntity findByIdQNA(String idQNA);
	@Query("select q.idQNA from QuincenaCatEntity q")
	public List<String> findByAllIdQNA();
	@Query("select q.idQNA from QuincenaCatEntity q where :hoy BETWEEN  q.fechaInicio and q.fechaFin")
	public String findQNAACT(@Param("hoy") Date hoy);
}
