package com.gestion.empleados.repository;

import org.springframework.data.repository.CrudRepository;

import com.gestion.empleados.JPA.Usuarios;

public interface UserRepository extends CrudRepository<Usuarios, String> {

}
