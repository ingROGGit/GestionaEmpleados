package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "puestos", catalog = "db_gestion_empleados2", schema = "public",uniqueConstraints= {@UniqueConstraint(name="UC_PP",columnNames={"puesto"})},indexes = {@Index(name="index_puestos",columnList = "puesto")})
@EntityListeners({ AuditingEntityListener.class})
public class PuestosEntity extends AuditableDateEntity implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "puesto")
    protected String puesto;
	@OneToMany(mappedBy = "puestosEntity")
	private List<Empleados> LEmpleados = new ArrayList<>();
	@Override
	public String toString() {
		return "PuestosEntity [id=" + id + ", puesto=" + puesto + "]";
	}
}
