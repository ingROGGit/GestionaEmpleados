package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.DetallePersepcionesEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenaCatEntity;

public interface DetallePersepcionesRepositoryJPA extends JpaRepository<DetallePersepcionesEntity, Serializable> {
	
	public List<DetallePersepcionesEntity> findByEmpleadoPerAndQuincenaCatDP(Empleados empleado, QuincenaCatEntity quinCat);
}
