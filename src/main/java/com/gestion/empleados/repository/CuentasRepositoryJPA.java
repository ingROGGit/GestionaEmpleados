package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.Empleados;


public interface CuentasRepositoryJPA extends JpaRepository<CuentasEntity, Serializable> {
	public CuentasEntity findByCuenta(String cuenta);
	public CuentasEntity findByEmpleadoC_Id(Long idEmpleado);
	public List<CuentasEntity> findByEmpleadoC(Empleados empleado);
}
