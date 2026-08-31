package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.DeduccionesEntity;

public interface DeduccionesRepositoryJPA  extends JpaRepository<DeduccionesEntity, Serializable>{
    public DeduccionesEntity findById(Long IDDeduccion);
    public DeduccionesEntity findByClave(String clave);
    public DeduccionesEntity findByClaveAndDescripcion(String clave,String descripcion);
}
