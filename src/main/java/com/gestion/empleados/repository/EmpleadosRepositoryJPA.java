/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gestion.empleados.repository;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author ROLIVAREZ
 */
@Repository
public interface EmpleadosRepositoryJPA extends JpaRepository<Empleados, Serializable>{
    public Empleados findById(Long IDEmpleado);
    public List<Empleados> findByNombreCompletoContainingIgnoreCaseOrCurpContainingIgnoreCaseOrRfcContainingIgnoreCase(String nom,String curp,String rfc);
    public List<Empleados> findByTipoContrato(String tipoContrato);
    @Query("SELECT DISTINCT e FROM Empleados e " +
            "JOIN e.quincenas q " +
            "JOIN q.quinCat cat " +
            "WHERE e.id=:idEmp and cat.idQNA = :idQNA")
    public Empleados findByIdAndQuincenaCatId(@Param("idEmp") Long idEmp,@Param("idQNA") String idQNA);
    @Query("SELECT q FROM Empleados e " +
    	       "JOIN e.quincenas q " +
    	       "JOIN q.quinCat cat " +
    	       "WHERE e.id = :idEmp AND cat.idQNA = :idQNA")
   public QuincenasEntity findQuincenaByEmpleadoAndCatId(@Param("idEmp") Long idEmp, @Param("idQNA") String idQNA);
   public Page<Empleados> findByLisCuentasIsEmpty(Pageable page);
   public Page<Empleados> findByDomiciliosIsEmpty(Pageable page);
   public Page<Empleados> findBySinavidIsNull(Pageable page);
   public Page<Empleados> findByDomiciliosIsNullAndSinavidIsNull(Pageable page);
   public Page<Empleados> findByDomiciliosIsNotNullAndSinavidIsNullOrSinavid_Estatus(Pageable page,String estatus);
   public List<Empleados> findByDomiciliosIsNotNullAndSinavidIsNullOrSinavid_Estatus(String estatus);
   @Query("SELECT e FROM Empleados e JOIN FETCH e.lisCuentas c WHERE c.estatus = :estatus")
   public Page<Empleados> findByLisCuentasEstatus(@Param("estatus") String estatus, Pageable pageable);
   @Query("SELECT e FROM Empleados e JOIN FETCH e.lisCuentas c WHERE c.estatus = :estatus")
   public List<Empleados> findByLisCuentas_Estatus(@Param("estatus") String estatus);
   public Page<Empleados> findByDomiciliosIsNotNull(Pageable pageable);
   public List<Empleados> findByDomiciliosIsNotNull();
   public Page<Empleados> findByTelefonoIsNullOrTelefono(String telefono,Pageable pageable);
   public Empleados findByCurp(String curp);
   public Page<Empleados> findBySinavid_Estatus(Pageable page,String estatus);
   public List<Empleados> findBySinavid_Estatus(String estatus);
   public List<Empleados> findBySinavid_EstatusAndSinavid_FechaBajaIsNotNull(String estatus);
   public Long countBySinavidIsNull();
   @Query("SELECT e FROM Empleados e " +
	       "JOIN FETCH e.quincenas q " +
	       "JOIN q.quinCat cat " +
	       "JOIN e.sinavid s " +
	       "WHERE cat.idQNA = :idQNA AND q.sueldoBase > s.sueldoSINAVID AND s.estatus<>:estatus")
   public List<Empleados> findByModificacionSalario(@Param("idQNA") String idQNA,@Param("estatus") String estatus);
   @Query("SELECT e FROM Empleados e " +
	       "JOIN FETCH e.quincenas q " +
	       "JOIN q.quinCat cat " +
	       "JOIN e.sinavid s " +
	       "WHERE cat.idQNA = :idQNA ")
   public List<Empleados> findByEMpleadosXQuincena(@Param("idQNA") String idQNA);
   public Page<Empleados> findByQuincenas_TesonTrue(Pageable pageRequest);
}
