package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.gestion.empleados.listener.AuditorySINAVIDListener;

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
@Table(name = "SINAVID", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class, AuditorySINAVIDListener.class})
public class SINAVIDEntity extends AuditableDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Size(max = 250)
	@Column(name = "estatus")
	private String estatus;
	@Column(name = "nss")
	protected String nss;
	@Column(name = "numISSSTE")
	protected String numISSSTE;
	@Basic(optional = false)
	@Column(name = "sueldoSINAVID")
	private BigDecimal sueldoSINAVID;
	@Basic(optional=false)
	@Column(name= "sueldoSAR")
	private BigDecimal sueldoSAR;
	@Basic(optional=false)
	@Column(name= "remTotal")
	private BigDecimal remTotal;
	@Basic(optional = true)
	@Column(name = "fechaRegistro")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;
	@Basic(optional = true)
	@Column(name = "fechaRespuesta")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRespuesta;
	@Size(max = 10)
	@Column(name = "pagaduria")
	private String pagaduria;
	@Size(max = 100)
	@Column(name = "alta")
	private String alta;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_Empleado") // This remains the owning side, holds the FK
	private Empleados sinavidEm;
	@Override
	public String toString() {
		return "SINAVIDEntity [id=" + id + ", estatus=" + estatus + ", nss=" + nss + ", numISSSTE=" + numISSSTE
				+ ", sueldoSINAVID=" + sueldoSINAVID + ", sueldoSAR=" + sueldoSAR + ", remTotal=" + remTotal
				+ ", fechaRegistro=" + fechaRegistro + ", fechaRespuesta=" + fechaRespuesta + ", pagaduria=" + pagaduria
				+ ", alta=" + alta + "]";
	}
}
