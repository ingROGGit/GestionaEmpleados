package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.DomiciliosEntity;


public interface DomicilioRepositoryJPA extends JpaRepository<DomiciliosEntity, Serializable>{

}
