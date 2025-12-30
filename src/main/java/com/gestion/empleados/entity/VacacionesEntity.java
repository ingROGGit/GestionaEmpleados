package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Vacaciones", catalog = "db_gestion_empleados", schema = "public")
@EntityListeners({ AuditingEntityListener.class})
public class VacacionesEntity extends AuditableDateEntity implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Basic(optional = false)
	@Column(name = "diasVacaciones")
	private int diasVacaciones;
	@Basic(optional = false)
	@Column(name = "diasDisfrutados")
	private int diasDisfrutados;
	@Basic(optional = false)
	@Column(name = "diasrestantes")
	private int diasRestantes;
	@Basic(optional = true)
	@Column(name = "dia1")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia1;
	@Basic(optional = true)
	@Column(name = "dia2")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia2;
	@Basic(optional = true)
	@Column(name = "dia3")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia3;
	@Basic(optional = true)
	@Column(name = "dia4")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia4;
	@Basic(optional = true)
	@Column(name = "dia5")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia5;
	@Basic(optional = true)
	@Column(name = "dia6")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia6;
	@Basic(optional = true)
	@Column(name = "dia7")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia7;
	@Basic(optional = true)
	@Column(name = "dia8")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia8;
	@Basic(optional = true)
	@Column(name = "dia9")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia9;
	@Basic(optional = true)
	@Column(name = "dia10")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia10;
	@Basic(optional = true)
	@Column(name = "dia11")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia11;
	@Basic(optional = true)
	@Column(name = "dia12")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia12;
	@Basic(optional = true)
	@Column(name = "dia13")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia13;
	@Basic(optional = true)
	@Column(name = "dia14")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia14;
	@Basic(optional = true)
	@Column(name = "dia15")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia15;
	@Basic(optional = true)
	@Column(name = "dia16")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia16;
	@Basic(optional = true)
	@Column(name = "dia17")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia17;
	@Basic(optional = true)
	@Column(name = "dia18")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia18;
	@Basic(optional = true)
	@Column(name = "dia19")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia19;
	@Basic(optional = true)
	@Column(name = "dia20")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dia20;
	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_Empleado") // This remains the owning side, holds the FK
    private Empleados empleadoV;
}
