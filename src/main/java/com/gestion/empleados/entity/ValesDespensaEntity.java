package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "ValesDespensaEntity", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class , AuditoryValesDespensaListener.class })
public class ValesDespensaEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	@Basic(optional = false)
	@Column(name = "id")
	private UUID id;
	@Column(name = "folio")
    protected String folio;
	@Column(name = "scaner")
    protected String scaner;
	@Column(name= "importe")
	private BigDecimal importe;
	@Column(name = "entregado")
	private Boolean entregado;
	@Column(name = "nLote")
	private int nLote;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Empleado", nullable = true)
    private Empleados empleado;
    @Column(name="fechaEntrega")
    private LocalDateTime fechaEntrega;
    @Column(name="fechaRegistro")
    private LocalDateTime fechaRegistro;
	@Override
	public String toString() {
		return "BancosEntity [id=" + id + ", folio=" + folio + ", Empleado=" + empleado.getId()+" "+empleado.getNombre() + "]";
	}
}
