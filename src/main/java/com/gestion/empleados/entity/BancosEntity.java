package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Bancos", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class })
public class BancosEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@NotNull
	@Column(name = "banco")
    protected String banco;
	@Column(name = "nombre")
    protected String nombre;
	@OneToMany(mappedBy = "bancoE")
	private List<CuentasEntity> LCuentas = new ArrayList<>();
	@Override
	public String toString() {
		return "BancosEntity [id=" + id + ", banco=" + banco + ", nombre=" + nombre + "]";
	}
}
