package com.gestion.empleados.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryCuentasListener;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cuentas", catalog = "db_gestion_empleados2", schema = "public",indexes = {
	    @Index(name="index_estatusCuenta",columnList = "estatus")})
@EntityListeners({ AuditingEntityListener.class, AuditoryCuentasListener.class})
public class CuentasEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "alta")
	private String alta;
	@Column(name = "estatus")
	private String estatus;
	@Column(name = "cuenta")
	private String cuenta;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_banco")
	private  BancosEntity bancoE;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Empleado")
	private  Empleados empleadoC;
	@Override
	public String toString() {
		return "CuentasEntity [id=" + id + ", alta=" + alta + ", estatus=" + estatus + ", cuenta=" + cuenta + "]";
	}
}
