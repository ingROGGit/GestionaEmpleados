package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.VacacionesEntity;
@Repository
public interface VacacionesRepository extends JpaRepository<VacacionesEntity, Serializable> {
}
