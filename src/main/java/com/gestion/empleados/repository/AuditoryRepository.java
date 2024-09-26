package com.gestion.empleados.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.entity.AuditoryEntity;

@Repository
public interface AuditoryRepository extends CrudRepository<AuditoryEntity, Integer> {

}
