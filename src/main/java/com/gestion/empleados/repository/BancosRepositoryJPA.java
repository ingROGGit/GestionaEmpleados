package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.BancosEntity;

public interface BancosRepositoryJPA extends JpaRepository<BancosEntity, Serializable>{
	public BancosEntity findByBanco(String banco);
}
