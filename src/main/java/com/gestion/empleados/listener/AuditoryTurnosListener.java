package com.gestion.empleados.listener;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gestion.empleados.entity.AuditoryEntity;
import com.gestion.empleados.entity.TurnosEntity;
import com.gestion.empleados.repository.AuditoryRepository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuditoryTurnosListener {
	private final AuditoryRepository auditoryRepository;

	@PrePersist
	private void prePersist(TurnosEntity auditory) {
		AuditoryEntity auditoryEntity = this.getAuditory("INSERT", auditory);
		this.auditoryRepository.save(auditoryEntity);
	}

	@PreUpdate
	private void preUpdate(TurnosEntity auditory) {
		AuditoryEntity auditoryEntity = this.getAuditory("UPDATE", auditory);
		this.auditoryRepository.save(auditoryEntity);
	}

	@PreRemove
	private void preRemove(TurnosEntity auditory) {
		AuditoryEntity auditoryEntity = this.getAuditory("DELETE", auditory);
		this.auditoryRepository.save(auditoryEntity);
	}

	private AuditoryEntity getAuditory(String operacion, TurnosEntity auditory) {
		AuditoryEntity auditoryEntity = new AuditoryEntity();
		String usuLoguin;
		try {
			usuLoguin=SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception err) {
			usuLoguin="";
		}
		auditoryEntity.setOperation(operacion);
		auditoryEntity.setFecha(LocalDateTime.now());
		auditoryEntity.setName(auditory.getId().toString());
		auditoryEntity.setUsu(usuLoguin);
		auditoryEntity.setDetalle(auditory.toString());
		return auditoryEntity;
	}
}
