package com.gestion.empleados.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryAsignaTurnosListener;
import com.gestion.empleados.listener.AuditoryTESONESEntityListener;

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
@Table(name = "TESONESEntity", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class,AuditoryTESONESEntityListener.class})
public class TESONESEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Size(max = 20)
	@Column(name = "estatus")
	private String estatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleados empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipoN", nullable = false)
    private TipoNomTESONEntity tipoNomTESONEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_motivo", nullable = false)
    private MotivoTESONEntity motivoTESONEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Quincena", nullable = false)
    private QuincenasEntity quincena;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_QNACat", nullable = false)
    private QuincenaCatEntity quinCat;
    
    private LocalDate fechaRegistro;
	@Override
	public String toString() {
		return "AsignacionTurnoEntity [id=" + id + ", fechaRegistro=" + fechaRegistro + "]";
	}

}
