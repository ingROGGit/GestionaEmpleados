package com.gestion.empleados.utils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.empleados.entity.BancosEntity;
import com.gestion.empleados.entity.CatCPJALEntity;
import com.gestion.empleados.entity.DeduccionesEntity;
import com.gestion.empleados.entity.DetalleDeduccionesEntiy;
import com.gestion.empleados.entity.DetallePersepcionesEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.PersepcionesEntity;
import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.entity.ServiciosEntity;
import com.gestion.empleados.entity.TurnosEntity;
import com.gestion.empleados.entity.VacacionesEntity;
import com.gestion.empleados.repository.BancosRepositoryJPA;
import com.gestion.empleados.repository.CatCPJALRepositoryJPA;
import com.gestion.empleados.repository.DeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetalleDeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetallePersepcionesRepositoryJPA;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.PersepcionesRepositoryJPA;
import com.gestion.empleados.repository.PuestosRepositoryJPA;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.ReglasDiasRepository;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;
import com.gestion.empleados.repository.TurnosRepositoryJPA;
import com.gestion.empleados.repository.VacacionesRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcesServiceTransactional {
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private TurnosRepositoryJPA trunosRJPA;
	@Autowired
	private PuestosRepositoryJPA puestosJPA;
	@Autowired
	private ServiciosRepositoryJPA serviciosJPA;
	@Autowired
	private VacacionesRepository vacacionesRJPA;
	@Autowired
	private ReglasDiasRepository reglasDiasRJPA;
	@Autowired
	private QuincenasCatRepositoryJPA quincenasCatJPA;
	@Autowired
	private PersepcionesRepositoryJPA persepcionesJPA;
	@Autowired
	private DeduccionesRepositoryJPA deduccionesJPA;
	@Autowired
	private BancosRepositoryJPA bancosJPA;
	@Autowired
	private CatCPJALRepositoryJPA catCPJALRepositoryJPA;
	@Autowired
	private DetallePersepcionesRepositoryJPA detallePerJPA;
	@Autowired
	private DetalleDeduccionesRepositoryJPA detalleDedJPA;
	@Autowired
	private QuincenaRepositoryJPA quincenaJPA;
//	public ProcesServiceTransactional(EmpleadosRepositoryJPA empleadosJPA,
//	TurnosRepositoryJPA trunosRJPA,
//	PuestosRepositoryJPA puestosJPA,
//	ServiciosRepositoryJPA serviciosJPA,
//	VacacionesRepository vacacionesRJPA,
//	ReglasDiasRepository reglasDiasRJPA,
//	QuincenasCatRepositoryJPA quincenasCatJPA,
//	PersepcionesRepositoryJPA persepcionesJPA,
//	DeduccionesRepositoryJPA deduccionesJPA,
//	BancosRepositoryJPA bancosJPA,
//	CatCPJALRepositoryJPA catCPJALRepositoryJPA,
//	DetallePersepcionesRepositoryJPA detallePerJPA,
//	DetalleDeduccionesRepositoryJPA detalleDedJPA,
//	QuincenaRepositoryJPA quincenaJPA) {
//		this.empleadosJPA=empleadosJPA;
//		this.trunosRJPA=trunosRJPA;
//		this.puestosJPA=puestosJPA;
//		this.serviciosJPA=serviciosJPA;
//		this.vacacionesRJPA=vacacionesRJPA;
//		this.reglasDiasRJPA=reglasDiasRJPA;
//		this.quincenasCatJPA=quincenasCatJPA;
//		this.persepcionesJPA=persepcionesJPA;
//		this.deduccionesJPA=deduccionesJPA;
//		this.bancosJPA=bancosJPA;
//		this.catCPJALRepositoryJPA=catCPJALRepositoryJPA;
//		this.detallePerJPA=detallePerJPA;
//		this.detalleDedJPA=detalleDedJPA;
//		this.quincenaJPA=quincenaJPA;
//	}
	@Transactional
	public void saveEmpleado(Empleados emp) {
		this.empleadosJPA.save(emp);
//		this.empleadosJPA.saveAndFlush(emp);
	}
}
