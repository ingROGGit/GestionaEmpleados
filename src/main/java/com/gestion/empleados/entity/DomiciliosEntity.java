package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.empleados.listener.AuditoryEmpleadosListener;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Domicilios", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class})
public class DomiciliosEntity extends AuditableDateEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@NotNull
	@Size(max = 255)
	@Column(name = "calle")
	private String calle;
	@NotNull
	@Size(max = 10)
	@Column(name = "numExt")
	private String numExt;
	@Size(max = 10)
	@Column(name = "numInt")
	private String numInt;
	@NotNull
	@Size(max = 255)
	@Column(name = "colonia")
	private String colonia;
	@NotNull
	@Size(max = 5)
	@Column(name = "cp")
	private String cp;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Empleado") // This remains the owning side, holds the FK
	private Empleados domiEm;
	@Override
	public String toString() {
		return "DomiciliosEntity [id=" + id + ", calle=" + calle + ", numExt=" + numExt + ", numInt=" + numInt
				+ ", colonia=" + colonia + ", cp=" + cp + "]";
	}
}
