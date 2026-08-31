package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.CatCPJALEntity;

public interface CatCPJALRepositoryJPA extends JpaRepository<CatCPJALEntity, Serializable>{
	public CatCPJALEntity findByCpAndColonia(String cp,String colonia);
}
