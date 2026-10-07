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
	public List<QuincenasEntity> findByQuinCat(QuincenaCatEntity quinCat);
	public Page<QuincenasEntity> findByQuinCatAndEmpleadoQN_Id(QuincenaCatEntity quinCat,Long idEMpleado, Pageable pageable);
	public Page<QuincenasEntity> findByQuinCatAndEmpleadoQNIn(QuincenaCatEntity quinCat,Collection<Empleados> cllectEmp,Pageable pageable);
	public List<QuincenasEntity> findByQuinCatAndEmpleadoQNIn(QuincenaCatEntity quinCat,Collection<Empleados> cllectEmp);
	public Page<QuincenasEntity> findByQuinCatAndTipoPago(QuincenaCatEntity quinCat,String tipoPago, Pageable pageable);
	public List<QuincenasEntity> findByQuinCatAndTipoPago(QuincenaCatEntity quinCat,String tipoPago);
	public Page<QuincenasEntity> findByQuinCatAndBaja(QuincenaCatEntity quinCat,boolean baja, Pageable pageable);
	public List<QuincenasEntity> findByQuinCatAndBaja(QuincenaCatEntity quinCat,boolean baja);
	public Page<QuincenasEntity> findByBloqueoPago(boolean bloqueado , Pageable pageable);
	public List<QuincenasEntity> findByBloqueoPago(boolean bloqueado);
	public QuincenasEntity findByQuinCatAndEmpleadoQN_Id(QuincenaCatEntity quinCat, Long idEMpleado);
	public Long countByTipoPagoAndQuinCat_IdQNA(String tipo,String idQNA);
	public Long countByQuinCat_IdQNAAndSueldoNetoIsNotNull(String idQNA);
	@Query("SELECT q.folioQuin FROM QuincenasEntity q  "+
			 "JOIN q.quinCat cat " +
			"WHERE cat.idQNA = :idQNA AND q.tipoPago = :tipoPago ")
    public List<String> findByFolisLong(@Param("idQNA") String idQNA,@Param("tipoPago") String tipoPago);
	@Query(value = "select q1_0.id,q1_0.baja,q1_0.bloqueo_pago,q1_0.created_date,q1_0.fecha_registro,q1_0.folio_pre,q1_0.folio_quin,\r\n"
			+ "q1_0.modified_date,q1_0.quinquenio,q1_0.salario_an,q1_0.salario_men,q1_0.salarioqn,\r\n"
			+ "q1_0.sueldo_base,q1_0.sueldo_neto,q1_0.sueldo_pre,q1_0.teson,q1_0.tipo_pago from quincenas q1_0  "
			+ " join (quincenas_qncat q2_0 join public.quincena_cat q2_1 on q2_1.idqna=q2_0.qna_cat_id) on q1_0.id=q2_0.quincena_id "
			+ " join quincenas_empleadoqn e1_0 on q1_0.id=e1_0.quincenas_id "
			+ " join public.detalle_persepciones_entity d1_0 on q2_1.idqna=d1_0.quincena_cat_id and d1_0.empleado_id=e1_0.empleadoqn_id "
			+ " join percepciones p1_0 on p1_0.id=d1_0.persepcion_id " +
			"where q2_0.qna_cat_id=:idQNA and p1_0.clave=:clavePer ",
			 countQuery ="select count(*) from public.quincenas q1_0  "
						+ " join (quincenas_qncat q2_0 join public.quincena_cat q2_1 on q2_1.idqna=q2_0.qna_cat_id) on q1_0.id=q2_0.quincena_id "
						+ " join quincenas_empleadoqn e1_0 on q1_0.id=e1_0.quincenas_id "
						+ " join public.detalle_persepciones_entity d1_0 on q2_1.idqna=d1_0.quincena_cat_id and d1_0.empleado_id=e1_0.empleadoqn_id "
						+ " join public.percepciones p1_0 on p1_0.id=d1_0.persepcion_id " +
						"where q2_0.qna_cat_id=:idQNA and p1_0.clave=:clavePer "
			,nativeQuery = true)
	public Page<QuincenasEntity> findByQuincenasPorClavePersepcion(@Param("idQNA") String idQNA,@Param("clavePer") String clavePer, Pageable pageable);
	@Query(value = "select q1_0.id,q1_0.baja,q1_0.bloqueo_pago,q1_0.created_date,q1_0.fecha_registro,q1_0.folio_pre,q1_0.folio_quin,\r\n"
			+ "q1_0.modified_date,q1_0.quinquenio,q1_0.salario_an,q1_0.salario_men,q1_0.salarioqn,\r\n"
			+ "q1_0.sueldo_base,q1_0.sueldo_neto,q1_0.sueldo_pre,q1_0.teson,q1_0.tipo_pago from quincenas q1_0  "
			+ " join (quincenas_qncat q2_0 join public.quincena_cat q2_1 on q2_1.idqna=q2_0.qna_cat_id) on q1_0.id=q2_0.quincena_id "
			+ " join quincenas_empleadoqn e1_0 on q1_0.id=e1_0.quincenas_id "
			+ " join public.detalle_persepciones_entity d1_0 on q2_1.idqna=d1_0.quincena_cat_id and d1_0.empleado_id=e1_0.empleadoqn_id "
			+ " join percepciones p1_0 on p1_0.id=d1_0.persepcion_id " +
			"where q2_0.qna_cat_id=:idQNA and p1_0.clave=:clavePer "
			,nativeQuery = true)
	public List<QuincenasEntity> findByQuincenasPorClavePersepcion(@Param("idQNA") String idQNA,@Param("clavePer") String clavePer);
	@Query(value = "select q1_0.id,q1_0.baja,q1_0.bloqueo_pago,q1_0.created_date,q1_0.fecha_registro,q1_0.folio_pre,q1_0.folio_quin,\r\n"
			+ "q1_0.modified_date,q1_0.quinquenio,q1_0.salario_an,q1_0.salario_men,q1_0.salarioqn,\r\n"
			+ "q1_0.sueldo_base,q1_0.sueldo_neto,q1_0.sueldo_pre,q1_0.teson,q1_0.tipo_pago from quincenas q1_0  "
			+ " join (quincenas_qncat q2_0 join public.quincena_cat q2_1 on q2_1.idqna=q2_0.qna_cat_id) on q1_0.id=q2_0.quincena_id "
			+ " join quincenas_empleadoqn e1_0 on q1_0.id=e1_0.quincenas_id "
			+ " join public.detalle_deducciones_entiy d1_0 on q2_1.idqna=d1_0.quincena_cat_id and d1_0.empleado_id=e1_0.empleadoqn_id "
			+ " join deducciones p1_0 on p1_0.id=d1_0.deduccion_id " +
			"where q2_0.qna_cat_id=:idQNA and p1_0.clave=:claveDed ",
			 countQuery ="select count(*) from public.quincenas q1_0  "
						+ " join (quincenas_qncat q2_0 join public.quincena_cat q2_1 on q2_1.idqna=q2_0.qna_cat_id) on q1_0.id=q2_0.quincena_id "
						+ " join quincenas_empleadoqn e1_0 on q1_0.id=e1_0.quincenas_id "
						+ " join public.detalle_deducciones_entiy d1_0 on q2_1.idqna=d1_0.quincena_cat_id and d1_0.empleado_id=e1_0.empleadoqn_id "
						+ " join public.deducciones p1_0 on p1_0.id=d1_0.deduccion_id " +
						"where q2_0.qna_cat_id=:idQNA and p1_0.clave=:claveDed "
			,nativeQuery = true)
	public Page<QuincenasEntity> findByQuincenasPorClaveDeduccion(@Param("idQNA") String idQNA,@Param("claveDed") String claveDed, Pageable pageable);
	@Query(value = "select q1_0.id,q1_0.baja,q1_0.bloqueo_pago,q1_0.created_date,q1_0.fecha_registro,q1_0.folio_pre,q1_0.folio_quin,\r\n"
			+ "q1_0.modified_date,q1_0.quinquenio,q1_0.salario_an,q1_0.salario_men,q1_0.salarioqn,\r\n"
			+ "q1_0.sueldo_base,q1_0.sueldo_neto,q1_0.sueldo_pre,q1_0.teson,q1_0.tipo_pago from quincenas q1_0  "
			+ " join (quincenas_qncat q2_0 join public.quincena_cat q2_1 on q2_1.idqna=q2_0.qna_cat_id) on q1_0.id=q2_0.quincena_id "
			+ " join quincenas_empleadoqn e1_0 on q1_0.id=e1_0.quincenas_id "
			+ " join public.detalle_deducciones_entiy d1_0 on q2_1.idqna=d1_0.quincena_cat_id and d1_0.empleado_id=e1_0.empleadoqn_id "
			+ " join deducciones p1_0 on p1_0.id=d1_0.deduccion_id " +
			"where q2_0.qna_cat_id=:idQNA and p1_0.clave=:claveDed "
			,nativeQuery = true)
	public List<QuincenasEntity> findByQuincenasPorClaveDeduccion(@Param("idQNA") String idQNA,@Param("claveDed") String claveDed);
}
