/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.gestion.empleados.listener.AuditoryEmpleadosListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ROLIVAREZ
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados", catalog = "db_gestion_empleados", schema = "public")
@EntityListeners({ AuditingEntityListener.class, AuditoryEmpleadosListener.class })
@NamedQueries({ @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e") })
public class Empleados extends AuditableDateEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@Column(name = "edad")
	private int edad;
	@Basic(optional = false)
	@NotNull
	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
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

//	public Empleados() {
//	}
//
//	public Empleados(Long id) {
//		this.id = id;
//	}
//
//	public Empleados(Long id, int edad, Date fecha, BigDecimal salario, String telefono) {
//		this.id = id;
//		this.edad = edad;
//		this.fecha = fecha;
//		this.salario = salario;
//		this.telefono = telefono;
//	}
//
//	@Override
//	public int hashCode() {
//		int hash = 0;
//		hash += (id != null ? id.hashCode() : 0);
//		return hash;
//	}
//
//	@Override
//	public boolean equals(Object object) {
//		// TODO: Warning - this method won't work in the case the id fields are not set
//		if (!(object instanceof Empleados)) {
//			return false;
//		}
//		Empleados other = (Empleados) object;
//		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public String toString() {
		return "ID=" + this.id + " APM=" + this.apellidom + " APP=" + this.apellidop + " NOMBRE=" + this.nombre
				+ " SEXO=" + this.sexo + " CORREO=" + this.correo + " EDAD=" + this.edad + " FECHA=" + this.fecha
				+ " SALARIO=" + this.salario + " TELEFONO=" + this.telefono;
	}

}
