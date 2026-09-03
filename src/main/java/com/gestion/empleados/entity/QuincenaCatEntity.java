package com.gestion.empleados.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QuincenaCat", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class })
public class QuincenaCatEntity extends AuditableDateEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "idQNA")
	@Size(max = 12)
	private String idQNA;
	@NotNull
	@Column(name = "fechaInicio")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	@NotNull
	@Column(name = "fechaFin")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	@NotNull
	@Column(name = "fechaPago")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaPago;
	@NotNull
	@Column(name = "fechaCheque")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCheque;
	@NotNull
	@Column(name = "fechaSuspencion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaSuspencion;
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "quinCat", fetch = FetchType.LAZY)
	private Set<QuincenasEntity> quincenas;
	@OneToMany(mappedBy = "quincenaCatDD", fetch = FetchType.LAZY)
	private List<DetalleDeduccionesEntiy> detalleDeducciones;
	@OneToMany(mappedBy = "quincenaCatDP", fetch = FetchType.LAZY)
	private List<DetallePersepcionesEntity> detallePercepciones;
	@Override
	public String toString() {
		return "QuincenaCatEntity [idQNA=" + idQNA + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", fechaPago=" + fechaPago + ", fechaCheque=" + fechaCheque + ", fechaSuspencion=" + fechaSuspencion
				+ "]";
	}
}
