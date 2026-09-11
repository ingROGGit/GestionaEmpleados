package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.DetalleDeduccionesEntiy;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenaCatEntity;

public interface DetalleDeduccionesRepositoryJPA  extends JpaRepository<DetalleDeduccionesEntiy, Serializable> {
	public List<DetalleDeduccionesEntiy> findByEmpleadoDDAndQuincenaCatDD(Empleados empleado, QuincenaCatEntity quinCat);
	public List<DetalleDeduccionesEntiy> findByEmpleadoDDAndQuincenaCatDD_IdQNA(Empleados empleado, String idQNA);
}
