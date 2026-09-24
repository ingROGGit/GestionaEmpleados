package com.gestion.empleados.repository;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.SINAVIDEntity;


public interface SINAVIDRepositoryJPA extends JpaRepository<SINAVIDEntity, Serializable>{
	public SINAVIDEntity findById(Long id);
	public SINAVIDEntity findByNumISSSTE(String numISSSTE);
	public SINAVIDEntity findByEmpleado_Id(Long idEmpleado);
	public SINAVIDEntity findByEmpleado_Curp(String curp);
	public Long countByFechaAltaBetweenAndEstatus(Date fechain, Date fechafin,String estatus);
	public Long countByFechaAltaBetweenAndEstatusIsNull(Date fechain, Date fechafin);
	public Long countByFechaAltaBeforeAndEstatus(Date fechaActual,String estatus);
	public Long countByFechaAltaBeforeAndEstatusIsNull(Date fechaActual);
}
