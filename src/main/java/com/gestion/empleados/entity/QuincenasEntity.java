package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.gestion.empleados.listener.AuditoryQNAsListener;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Quincenas", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class, AuditoryQNAsListener.class })
public class QuincenasEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Basic(optional = true)
	@Column(name = "sueldoBase")
	private BigDecimal sueldoBase;
	@Basic(optional = true)
	@Column(name = "sueldoNeto")
	private BigDecimal sueldoNeto;
	@Column(name = "quinquenio")
	private boolean quinquenio;
	@Column(name = "baja")
	private boolean baja;
	@Column(name = "sueldoPre")
	private BigDecimal sueldoPre;
	@Size(max = 12)
	@Column(name = "tipoPago")
	private String tipoPago;
	@Size(max = 15)
	@Column(name = "folioPre")
	private String folioPre;
	@Size(max = 15)
	@Column(name = "folioQuin")
	private String folioQuin;
	@Column(name = "fechaRegistro")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;
	@Column(name = "salarioQN")
	private BigDecimal salarioQN;
	@Column(name = "salarioMen")
	private BigDecimal salarioMen;
	@Column(name = "salarioAn")
	private BigDecimal salarioAn;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "quincenas_empleadoqn", // Nombre de la tabla intermedia
			joinColumns = @JoinColumn(name = "quincenas_id"), // Clave foránea de esta entidad
			inverseJoinColumns = @JoinColumn(name = "empleadoqn_id") // Clave foránea de la otra entidad
	)
	private List<Empleados> empleadoQN;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Quincenas_QNCat", // Nombre de la tabla intermedia
			joinColumns = @JoinColumn(name = "quincena_id"), // Clave foránea de esta entidad
			inverseJoinColumns = @JoinColumn(name = "qnaCat_id") // Clave foránea de la otra entidad
	)
	private Set<QuincenaCatEntity> quinCat = new HashSet<>();

	@Override
	public String toString() {
		return "QuincenasEntity [id=" + id + ", sueldoBase=" + sueldoBase + ", sueldoNeto=" + sueldoNeto
				+ ", sueldoPre=" + sueldoPre + ", tipoPago=" + tipoPago + ", fechaRegistro=" + fechaRegistro + "]";
	}
}
