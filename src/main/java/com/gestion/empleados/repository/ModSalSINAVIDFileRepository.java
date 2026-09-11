package com.gestion.empleados.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleados.entity.AltaCuentasFilesEntity;
import com.gestion.empleados.entity.ModSalSINAVIDFilesEntity;



public interface ModSalSINAVIDFileRepository  extends JpaRepository<ModSalSINAVIDFilesEntity, Serializable>{

}
