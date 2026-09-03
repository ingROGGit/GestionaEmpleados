/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.ml.neuralnet.UpdateAction;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.gestion.empleados.listener.AuditoryEmpleadosListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ROLIVAREZ
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class, AuditoryEmpleadosListener.class })
public class Empleados extends AuditableDateEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;
	@Transient
	@Builder.Default
	private boolean isNew = true;
	@Transient
	@Builder.Default
	private boolean updateAction = false;
	@Column(name = "fechaIngreso")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaIngreso;
	@Column(name = "fechaIngresoH")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaIngresoH;
	@Column(name = "fechaNacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	@Size(max = 10)
	@Column(name = "telefonoEmer")
	private String telefonoEmer;
	@Size(max = 10)
	@Column(name = "telefono")
	private String telefono;
	@Size(max = 550)
	@Column(name = "nombreCompleto")
	private String nombreCompleto;
	@Size(max = 100)
	@Column(name = "apellidom")
	private String apellidom;
	@Size(max = 100)
	@Column(name = "apellidop")
	private String apellidop;
	@Size(max = 100)
	@Column(name = "correo")
	private String correo;
	@Size(max = 255)
	@Column(name = "nombre")
	private String nombre;
	@Size(max = 15)
	@Column(name = "sexo")
	private String sexo;
	@Size(max = 15)
	@Column(name = "plaza")
	private String plaza;
	@Size(max = 12)
	@Column(name = "claveP")
	private String claveP;
	@Size(max = 10)
	@Column(name = "tipoContrato")
	private String tipoContrato;
	@Column(name = "activo")
	private boolean activo;
	@Size(max = 18)
	@Column(name = "curp")
	private String curp;
	@Size(max = 14)
	@Column(name = "rfc")
	private String rfc;
	@OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
	private List<TurnosEntity> turnosEm = new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_puesto")
	private PuestosEntity puestosEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servicio")
	private ServiciosEntity servicioEntity;
	@OneToOne(mappedBy = "empleadoV", fetch = FetchType.LAZY)
	private VacacionesEntity empleadoV;
	@OneToOne(mappedBy = "sinavidEm", fetch = FetchType.LAZY)
	private SINAVIDEntity sinavidEm;
	@OneToOne(mappedBy = "domiEm", fetch = FetchType.LAZY, orphanRemoval = true)
	private DomiciliosEntity domicilios;
	@ManyToMany(mappedBy = "empleadoQN")
	private List<QuincenasEntity> quincenas;
	@OneToMany(mappedBy = "empleadoC")
	private List<CuentasEntity> LCuentas = new ArrayList<>();
	@OneToMany(mappedBy = "empleadoDD", fetch = FetchType.LAZY)
	private List<DetalleDeduccionesEntiy> detalleDeducciones;
	@OneToMany(mappedBy = "empleadoPer", fetch = FetchType.LAZY)
	private List<DetallePersepcionesEntity> detallePercepcione;

//	@Override
//	public boolean isNew() {
//		return !this.updateAction; 
//	}
//
//	@PostLoad
//			 
//	public void markNotNew() {
//		this.updateAction = true; 
//		this.isNew=false;
//	}
	
	@Override
	public String toString() {
		return "Empleados [id=" + id + ", isNew=" + isNew + ", updateAction=" + updateAction + ", fechaIngreso="
				+ fechaIngreso + ", fechaIngresoH=" + fechaIngresoH + ", fechaNacimiento=" + fechaNacimiento
				+ ", telefonoEmer=" + telefonoEmer + ", telefono=" + telefono + ", nombreCompleto=" + nombreCompleto
				+ ", apellidom=" + apellidom + ", apellidop=" + apellidop + ", correo=" + correo + ", nombre=" + nombre
				+ ", sexo=" + sexo + ", plaza=" + plaza + ", claveP=" + claveP + ", tipoContrato=" + tipoContrato
				+ ", activo=" + activo + ", curp=" + curp + ", rfc=" + rfc + ", empleadoV=" + empleadoV + "]";
	}

}
