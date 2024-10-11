package com.gestion.empleados.repository;

import org.springframework.data.repository.CrudRepository;

import com.gestion.empleados.entity.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer>{
	
}
