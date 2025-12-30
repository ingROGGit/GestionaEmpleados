package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.entity.TurnosEntity;

@Repository
public interface TurnosRepositoryJPA extends JpaRepository<TurnosEntity, Serializable>{
	public TurnosEntity findByDescripcion(String descripcion);
}
