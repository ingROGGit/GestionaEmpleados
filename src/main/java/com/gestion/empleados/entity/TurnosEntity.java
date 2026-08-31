package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryTurnosListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "Turnos", catalog = "db_gestion_empleados2", schema = "public",uniqueConstraints= {@UniqueConstraint(name="UC_TURHR",columnNames={"turno","horario"})})
@EntityListeners({ AuditingEntityListener.class,AuditoryTurnosListener.class})
public class TurnosEntity extends AuditableDateEntity implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "turno")
    protected String turno;
	@Column(name = "horario")
    protected String horario;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "turno_empleado", // Nombre de la tabla intermedia
	        joinColumns = @JoinColumn(name = "turno_id"), // FK a esta entidad (Turno)
	        inverseJoinColumns = @JoinColumn(name = "empleado_id") // FK a la otra entidad (Empresa)
	    )
	private List<Empleados> LTEmpledos = new ArrayList<>();

	@Override
	public String toString() {
		return "TurnosEntity [id=" + id + ", turno=" + turno + ", horario=" + horario + "]";
	}
}
