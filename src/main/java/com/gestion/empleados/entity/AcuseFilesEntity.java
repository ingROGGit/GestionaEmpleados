package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "AcuseFilesEntity", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class })
public class AcuseFilesEntity extends AuditableDateEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		@Id
	    @GeneratedValue(strategy = GenerationType.UUID)
		@Basic(optional = false)
		@Column(name = "id")
		private UUID id;
		@Column(name = "tipo", length = 1, columnDefinition = "char(1)",nullable=false)
		private char tipo;
		@Column(name = "fileAcuse", length = Integer.MAX_VALUE)
		private byte[] fileAcuse;
		@Column(name = "fileName")
		private String fileName;
		@Basic(optional = true)
		@Column(name = "fechaCarga")
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaCarga;
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "id_Empleado", nullable = false)
	    private Empleados empleado;
}
