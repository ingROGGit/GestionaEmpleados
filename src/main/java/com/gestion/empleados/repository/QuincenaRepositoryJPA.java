package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;

public interface QuincenaRepositoryJPA extends JpaRepository<QuincenasEntity, Serializable>,JpaSpecificationExecutor<QuincenasEntity> {
	public QuincenasEntity findById(Long id);
	public Page<QuincenasEntity> findByQuinCat(QuincenaCatEntity quinCat, Pageable pageable);
	public Page<QuincenasEntity> findByQuinCatAndEmpleadoQN_Id(QuincenaCatEntity quinCat,Long idEMpleado, Pageable pageable);
	public Page<QuincenasEntity> findByQuinCatAndEmpleadoQNIn(QuincenaCatEntity quinCat,Collection<Empleados> cllectEmp,Pageable pageable);
	public Page<QuincenasEntity> findByQuinCatAndTipoPago(QuincenaCatEntity quinCat,String tipoPago, Pageable pageable);
	public Page<QuincenasEntity> findByQuinCatAndBaja(QuincenaCatEntity quinCat,boolean baja, Pageable pageable);
	public Page<QuincenasEntity> findByBloqueoPago(boolean bloqueado , Pageable pageable);
	public QuincenasEntity findByQuinCatAndEmpleadoQN_Id(QuincenaCatEntity quinCat, Long idEMpleado);
	public Long countByTipoPagoAndQuinCat_IdQNA(String tipo,String idQNA);
	public Long countByQuinCat_IdQNAAndSueldoNetoIsNotNull(String idQNA);
	@Query("SELECT q.folioQuin FROM QuincenasEntity q  "+
			 "JOIN q.quinCat cat " +
			"WHERE cat.idQNA = :idQNA AND q.tipoPago = :tipoPago ")
    public List<String> findByFolisLong(@Param("idQNA") String idQNA,@Param("tipoPago") String tipoPago);
}
