package com.gestion.empleados.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.gestion.empleados.entity.UsuariosEntity;

public interface UserRepository extends CrudRepository<UsuariosEntity, String> {
	
	Optional<UsuariosEntity> findByUsername(String username);
}
