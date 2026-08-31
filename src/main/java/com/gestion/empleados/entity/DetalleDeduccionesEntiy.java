package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryEmpleadosListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@Table(name = "DetalleDeduccionesEntiy", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class})	
public class DetalleDeduccionesEntiy extends AuditableDateEntity implements Serializable {
private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "importe")
	private BigDecimal importe;
	@ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleados empleadoDD;
	
	@ManyToOne
    @JoinColumn(name = "deduccion_id", nullable = false) 
    private DeduccionesEntity deducciones;
	
	@ManyToOne
    @JoinColumn(name = "quincenaCat_id", nullable = false) 
    private QuincenaCatEntity quincenaCatDD;

	@Override
	public String toString() {
		return "DetalleDeduccionesEntiy [id=" + id + ", importe=" + importe  + "]";
	}
}
