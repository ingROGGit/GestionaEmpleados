package com.gestion.empleados.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestion.empleados.entity.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Long> {

}
