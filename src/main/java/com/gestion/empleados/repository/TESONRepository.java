package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.TESONESEntity;



public interface TESONRepository  extends JpaRepository<TESONESEntity, Serializable>{
	public TESONESEntity findById(Long id);
	public Page<TESONESEntity> findAll(Pageable pageRequest);
	public Page<TESONESEntity> findByEstatus(String estatus,Pageable pageRequest);
	public List<TESONESEntity> findByEstatus(String estatus);
	public TESONESEntity findByEmpleado_IdAndQuincena_IdAndQuinCat_IdQNA(Long idEmp,Long idQuin,String idQNACat);
	public List<TESONESEntity> findByEstatusAndTipoNomTESONEntity_Id(String estatus,Long tipo);
}
