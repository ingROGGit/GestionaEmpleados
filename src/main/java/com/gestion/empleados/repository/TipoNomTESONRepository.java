package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.TipoNomTESONEntity;


public interface TipoNomTESONRepository extends JpaRepository<TipoNomTESONEntity, Serializable>{
	
}
