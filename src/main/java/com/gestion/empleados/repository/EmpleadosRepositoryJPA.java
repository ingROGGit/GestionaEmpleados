/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gestion.empleados.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestion.empleados.entity.Empleados;

/**
 *
 * @author ROLIVAREZ
 */
@Repository
public interface EmpleadosRepositoryJPA extends JpaRepository<Empleados, Serializable>{
    public Empleados findById(Long IDEmpleado);
    public List<Empleados> findByNombreCompletoContainingIgnoreCaseOrCurpContainingIgnoreCaseOrRfcContainingIgnoreCase(String nom,String curp,String rfc);
    public List<Empleados> findByTipoContrato(String tipoContrato);
}
