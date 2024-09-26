package com.gestion.empleados.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "AuditoryEntity", catalog = "db_gestion_empleados", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String operation;
	@Size(max = 500)
	private String detalle;
	private String usu;
	private LocalDateTime fecha;
	
}
 