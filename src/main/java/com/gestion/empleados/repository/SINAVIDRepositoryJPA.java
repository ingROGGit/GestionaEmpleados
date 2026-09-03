package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.SINAVIDEntity;


public interface SINAVIDRepositoryJPA extends JpaRepository<SINAVIDEntity, Serializable>{
	public SINAVIDEntity findByNumISSSTE(String numISSSTE);
}
