package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.ValesDespensaEntity;


public interface ValesDespensaRepositoryJPA extends JpaRepository<ValesDespensaEntity, Serializable>{
	public ValesDespensaEntity findByFolio(String folio);
}
