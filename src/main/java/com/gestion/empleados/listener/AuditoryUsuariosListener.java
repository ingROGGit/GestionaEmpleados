package com.gestion.empleados.listener;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gestion.empleados.entity.AuditoryEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.AuditoryRepository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuditoryUsuariosListener {
	private final AuditoryRepository auditoryRepository;
	@PrePersist
	private void prePersist(UsuariosEntity usuario) {
		AuditoryEntity auditoryEntity= this.getAuditory("INSERT",usuario);
		this.auditoryRepository.save(auditoryEntity);
	}
	@PreUpdate
	private void preUpdate(UsuariosEntity usuario) {
		AuditoryEntity auditoryEntity= this.getAuditory("UPDATE",usuario);
		this.auditoryRepository.save(auditoryEntity);
	}
	@PreRemove
	private void preRemove(UsuariosEntity usuario) {
		AuditoryEntity auditoryEntity= this.getAuditory("DELETE",usuario);
		this.auditoryRepository.save(auditoryEntity);
	}
	private AuditoryEntity getAuditory(String operacion,UsuariosEntity usuario) {
		AuditoryEntity auditoryEntity= new AuditoryEntity();
		auditoryEntity.setOperation(operacion);
		auditoryEntity.setFecha(LocalDateTime.now());
		auditoryEntity.setName(usuario.getUsername());
		auditoryEntity.setUsu(SecurityContextHolder.getContext().getAuthentication().getName());
		auditoryEntity.setDetalle(usuario.toString());
		return 	auditoryEntity;	
	}
}
