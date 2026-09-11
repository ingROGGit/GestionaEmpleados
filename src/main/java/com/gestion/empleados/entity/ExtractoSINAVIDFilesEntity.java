package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ExtractoSINAVIDFilesEntity", catalog = "db_gestion_empleados2", schema = "public")
@EntityListeners({ AuditingEntityListener.class })
public class ExtractoSINAVIDFilesEntity extends AuditableDateEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private Long id;
		@Column(name = "fileAlta", length = Integer.MAX_VALUE)
		private byte[] fileAlta;
		@Column(name = "fileName")
		private String fileName;
		@Basic(optional = true)
		@Column(name = "fechaGen")
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date fechaGen;
}
