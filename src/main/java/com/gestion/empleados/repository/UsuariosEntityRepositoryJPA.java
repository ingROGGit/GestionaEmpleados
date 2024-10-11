package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.UsuariosEntity;

public interface UsuariosEntityRepositoryJPA extends JpaRepository<UsuariosEntity, Serializable>{

}
