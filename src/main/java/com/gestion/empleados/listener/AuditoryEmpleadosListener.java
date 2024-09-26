package com.gestion.empleados.listener;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gestion.empleados.entity.AuditoryEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.repository.AuditoryRepository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuditoryEmpleadosListener {
	
	private final AuditoryRepository auditoryRepository;
	@PrePersist
	private void prePersist(Empleados empleados) {
		AuditoryEntity auditoryEntity= this.getAuditory("INSERT",empleados);
		this.auditoryRepository.save(auditoryEntity);
	}
	@PreUpdate
	private void preUpdate(Empleados empleados) {
		AuditoryEntity auditoryEntity= this.getAuditory("UPDATE",empleados);
		this.auditoryRepository.save(auditoryEntity);
	}
	@PreRemove
	private void preRemove(Empleados empleados) {
		AuditoryEntity auditoryEntity= this.getAuditory("DELETE",empleados);
		this.auditoryRepository.save(auditoryEntity);
	}
	private AuditoryEntity getAuditory(String operacion,Empleados empleados) {
		AuditoryEntity auditoryEntity= new AuditoryEntity();
		auditoryEntity.setOperation(operacion);
		auditoryEntity.setFecha(LocalDateTime.now());
		auditoryEntity.setName(empleados.getNombre());
		auditoryEntity.setUsu(SecurityContextHolder.getContext().getAuthentication().getName());
		auditoryEntity.setDetalle(empleados.toString());
		return 	auditoryEntity;	
	}
}
