package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.entity.HorariosEntity;

@Repository
public interface HorariosRepositoryJPA extends JpaRepository<HorariosEntity, Serializable>{
	public HorariosEntity findByHorario(String horario);
	public HorariosEntity findByCodigo(String codigo);
}
