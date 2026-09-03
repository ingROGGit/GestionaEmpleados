package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.CuentasEntity;


public interface CuentasRepositoryJPA extends JpaRepository<CuentasEntity, Serializable> {
	public CuentasEntity findByCuenta(String cuenta);
}
