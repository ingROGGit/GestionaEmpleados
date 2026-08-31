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
@Table(name = "servicios", catalog = "db_gestion_empleados2", schema = "public",uniqueConstraints= {@UniqueConstraint(name="UC_SS",columnNames={"servicio"})},indexes = {@Index(name="index_servicio",columnList = "servicio")})
@EntityListeners({ AuditingEntityListener.class})
public class ServiciosEntity extends AuditableDateEntity implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "servicio")
    protected String servicio;
	@OneToMany(mappedBy = "servicioEntity")
	private List<Empleados> LEmpleados = new ArrayList<>();
	@Override
	public String toString() {
		return "ServiciosEntity [id=" + id + ", servicio=" + servicio + "]";
	}
}
