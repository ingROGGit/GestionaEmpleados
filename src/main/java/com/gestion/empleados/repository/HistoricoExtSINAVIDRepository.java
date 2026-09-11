package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.HistoricoExtSINAVIDEntity;


public interface HistoricoExtSINAVIDRepository extends JpaRepository<HistoricoExtSINAVIDEntity, Serializable>{
	public HistoricoExtSINAVIDEntity findByEmpleado_IdAndFileName(Long idEmpleado,String filName);
}
