package com.gestion.empleados.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import com.gestion.empleados.listener.AuditoryHistoricoListener;

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
import jakarta.persistence.ManyToOne;
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
	@Table(name = "HistoricoExtSINAVIDEntity", catalog = "db_gestion_empleados2", schema = "public")
	@EntityListeners({ AuditingEntityListener.class, AuditoryHistoricoListener.class})
	public class HistoricoExtSINAVIDEntity extends AuditableDateEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Basic(optional = false)
		@Column(name = "id")
		private Long id;
		@Column(name = "fileName")
		private String fileName;
		@Basic(optional = true)
		@Column(name = "fechaGen")
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaGen;
		@Column(name = "nss")
		protected String nss;
		@Column(name = "numISSSTE")
		protected String numISSSTE;
		@Basic(optional = true)
		@Column(name = "sueldoISSSTE")
		private BigDecimal sueldoISSSTE;
		@Basic(optional=true)
		@Column(name= "sueldoSAR")
		private BigDecimal sueldoSAR;
		@Basic(optional=true)
		@Column(name= "remTotal")
		private BigDecimal remTotal;
		@Basic(optional = true)
		@Column(name = "fechaAlta")
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaAlta;
		@Basic(optional = true)
		@Column(name = "fechaModSueldo")
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaModSueldo;
		@Size(max = 10)
		@Column(name = "pagaduria")
		private String pagaduria;
		@Size(max = 100)
		@Column(name = "claveCobro")
		private String claveCobro;
		@Column(name = "tipoNombramiento")
		private int tipoNombramiento;
		@Column(name = "disCve")
		private int disCve;
		@Column(name = "numRamo")
		private int numRamo;
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "id_Empleado")
		private  Empleados empleado;
		@Override
		public String toString() {
			return "HistoricoExtSINAVIDEntity [id=" + id + ", fileName=" + fileName + ", fechaGen=" + fechaGen
					+ ", nss=" + nss + ", numISSSTE=" + numISSSTE + ", sueldoISSSTE=" + sueldoISSSTE + ", sueldoSAR="
					+ sueldoSAR + ", remTotal=" + remTotal + ", fechaAlta=" + fechaAlta + ", fechaModSueldo="
					+ fechaModSueldo + ", pagaduria=" + pagaduria + ", claveCobro=" + claveCobro + ", tipoNombramiento="
					+ tipoNombramiento + ", disCve=" + disCve + ", numRamo=" + numRamo + "]";
		}

}
