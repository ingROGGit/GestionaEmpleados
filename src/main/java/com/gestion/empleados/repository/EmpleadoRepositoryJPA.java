/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.gestion.empleados.repository;

import com.gestion.empleados.JPA.Empleados;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ROLIVAREZ
 */
@Repository
public interface EmpleadoRepositoryJPA extends JpaRepository<Empleados, Serializable>{
    
}
