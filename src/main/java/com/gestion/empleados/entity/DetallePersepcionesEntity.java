package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "DetallePersepcionesEntity", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class})	
public class DetallePersepcionesEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		@Id
		@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@Column(name = "importe")
		private BigDecimal importe;
		@ManyToOne
	    @JoinColumn(name = "empleado_id", nullable = false)
	    private Empleados empleadoPer;
		@ManyToOne
	    @JoinColumn(name = "persepcion_id", nullable = false) 
	    private PersepcionesEntity persepciones;
		@ManyToOne
	    @JoinColumn(name = "quincenaCat_id", nullable = false) 
	    private QuincenaCatEntity quincenaCatDP;
		@Override
		public String toString() {
			return "DetallePersepcionesEntity [id=" + id + ", importe=" + importe + "]";
		}
}
