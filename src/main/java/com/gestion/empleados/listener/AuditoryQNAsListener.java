package com.gestion.empleados.listener;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gestion.empleados.entity.AuditoryEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.repository.AuditoryRepository;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AuditoryQNAsListener {
	private final AuditoryRepository auditoryRepository;

	@PrePersist
	private void prePersist(QuincenasEntity auditory) {
		AuditoryEntity auditoryEntity = this.getAuditory("INSERT", auditory);
		this.auditoryRepository.save(auditoryEntity);
	}

	@PreUpdate
	private void preUpdate(QuincenasEntity auditory) {
		AuditoryEntity auditoryEntity = this.getAuditory("UPDATE", auditory);
		this.auditoryRepository.save(auditoryEntity);
	}

	@PreRemove
	private void preRemove(QuincenasEntity auditory) {
		AuditoryEntity auditoryEntity = this.getAuditory("DELETE", auditory);
		this.auditoryRepository.save(auditoryEntity);
	}

	private AuditoryEntity getAuditory(String operacion, QuincenasEntity auditory) {
		AuditoryEntity auditoryEntity = new AuditoryEntity();
		String usuLoguin;
		try {
			usuLoguin=SecurityContextHolder.getContext().getAuthentication().getName();
		}catch(Exception err) {
			usuLoguin="";
		}
		auditoryEntity.setOperation(operacion);
		auditoryEntity.setFecha(LocalDateTime.now());
		auditoryEntity.setName(auditory.getId()==null?"New": auditory.getId().toString());
		auditoryEntity.setUsu(usuLoguin);
		auditoryEntity.setDetalle(auditory.toString());
		return auditoryEntity;
	}
}
