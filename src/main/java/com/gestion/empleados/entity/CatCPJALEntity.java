package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "CatCPJAL", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class })
public class CatCPJALEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@NotNull
	@Column(name = "colonia")
    protected String colonia;
	@NotNull
	@Size(max = 5)
	@Column(name = "cp")
    protected String cp;
	@Override
	public String toString() {
		return "CatCPJALEntity [id=" + id + ", colonia=" + colonia + ", cp=" + cp + "]";
	}
}
