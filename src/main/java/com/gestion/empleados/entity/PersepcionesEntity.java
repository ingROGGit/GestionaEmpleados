package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryEmpleadosListener;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "Percepciones", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class })
public class PersepcionesEntity extends AuditableDateEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@NotNull
	@Size(max = 4)
	@Column(name = "clave")
	private String clave;
	@NotNull
	@Size(max = 250)
	@Column(name = "descripcion")
	private String descripcion;
	@OneToMany(mappedBy = "persepciones", cascade = CascadeType.ALL)
	private List<DetallePersepcionesEntity> detallePercepciones;
	@Override
	public String toString() {
		return "PersepcionesEntity [id=" + id + ", clave=" + clave + ", descripcion=" + descripcion + "]";
	}
}
