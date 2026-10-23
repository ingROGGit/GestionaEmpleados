package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryValesDespensaListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ValesDespensaEntity", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class , AuditoryValesDespensaListener.class })
public class ValesDespensaEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@NotNull
	@Column(name = "folio")
    protected String folio;
	@Column(name = "scaner")
    protected String scaner;
	@Column(name= "importe")
	private BigDecimal importe;
	@Column(name = "entregado")
	private Boolean nLote;
	@Column(name = "nLote")
	private Boolean entregado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Empleado", nullable = false)
    private Empleados empleado;
	@Override
	public String toString() {
		return "BancosEntity [id=" + id + ", folio=" + folio + ", Empleado=" + empleado.getId()+" "+empleado.getNombre() + "]";
	}
}
