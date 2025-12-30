/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.gestion.empleados.listener.AuditoryEmpleadosListener;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "empleados", catalog = "db_gestion_empleados", schema = "public",uniqueConstraints= {@UniqueConstraint(name="UC_Empleado",columnNames={"NEmpleado","nombre","apellidop","apellidom"})},indexes = {@Index(name="index_NEmpleado",columnList = "NEmpleado"),
	    @Index(name="index_nombre",columnList = "nombre"),@Index(name="index_apellidop",columnList = "apellidop"),@Index(name="index_apellidom",columnList = "apellidom")})
@EntityListeners({ AuditingEntityListener.class, AuditoryEmpleadosListener.class })
@NamedQueries({ @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e") })
public class Empleados extends AuditableDateEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "NEmpleado")
	private Long NEmpleado;
	@Basic(optional = false)
	@NotNull
	@Column(name = "edad")
	private int edad;
	@NotNull
	@Column(name = "fechaIngreso")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaIngreso;
	@NotNull
	@Column(name = "fechaNacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	@Basic(optional = false)
	@NotNull
	@Column(name = "salario")
	private BigDecimal salario;
	@Basic(optional = false)
	@NotNull
	@Size(max = 255)
	@Column(name = "telefono")
	private String telefono;
	@Size(max = 255)
	@Column(name = "apellidom")
	private String apellidom;
	@Size(max = 255)
	@Column(name = "apellidop")
	private String apellidop;
	@Size(max = 255)
	@Column(name = "correo")
	private String correo;
	@Size(max = 255)
	@Column(name = "nombre")
	private String nombre;
	@Size(max = 255)
	@Column(name = "sexo")
	private String sexo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_turno")
	private  TurnosEntity turnosEntity;
	@OneToOne(mappedBy = "empleadoV", fetch = FetchType.LAZY)
	private VacacionesEntity empleadoV;
	@Override
	public String toString() {
		return "ID=" + this.id + " APM=" + this.apellidom + " APP=" + this.apellidop + " NOMBRE=" + this.nombre
				+ " SEXO=" + this.sexo + " CORREO=" + this.correo + " EDAD=" + this.edad + " FECHA=" + this.fechaIngreso
				+ " SALARIO=" + this.salario + " TELEFONO=" + this.telefono;
	}

}
