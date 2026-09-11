package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.DomiciliosEntity;
import com.gestion.empleados.entity.Empleados;


public interface DomicilioRepositoryJPA extends JpaRepository<DomiciliosEntity, Serializable>{
	public DomiciliosEntity findByDomiEm_Id(Long idEmpleado);
}
