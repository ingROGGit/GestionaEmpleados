package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.AddBajaEntity;


public interface AddBajasRepository extends JpaRepository<AddBajaEntity, Serializable>{
	public AddBajaEntity findByEmpleado_Id(Long idEmpleado);
}
