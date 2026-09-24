package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.entity.AsignacionTurnoEntity;

@Repository
public interface AsignacionTurnosRepositoryJPA extends JpaRepository<AsignacionTurnoEntity, Serializable>{
	public List<AsignacionTurnoEntity> findByTurno_Codigo(String turno);
	public List<AsignacionTurnoEntity> findByHorario_Codigo(String horario);
	public List<AsignacionTurnoEntity> findByEmpleado_Id(Long idEmp);
}
