package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.PersepcionesEntity;

public interface PersepcionesRepositoryJPA extends JpaRepository<PersepcionesEntity, Serializable>{
    public PersepcionesEntity findById(Long IDPersepcion);
    public PersepcionesEntity findByClaveAndDescripcion(String clave,String descripcion);
    public PersepcionesEntity findByClave(String clave);
}
