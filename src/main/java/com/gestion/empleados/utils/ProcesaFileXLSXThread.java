package com.gestion.empleados.utils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.entity.TurnosEntity;
import com.gestion.empleados.entity.VacacionesEntity;
import com.gestion.empleados.repository.ReglasDiasRepository;
import com.gestion.empleados.repository.TurnosRepositoryJPA;
import com.gestion.empleados.repository.VacacionesRepository;
import com.gestion.empleados.service.EmpleadoService;

public class ProcesaFileXLSXThread extends Thread {

	private EmpleadoService empleadoService;

	private final File filesProces;
	private TurnosRepositoryJPA trunosRJPA;
	private VacacionesRepository vacacionesRJPA;
	private ReglasDiasRepository reglasDiasRJPA;

	public ProcesaFileXLSXThread(File fileProces, EmpleadoService empleadoService, TurnosRepositoryJPA trunosRJPA,
			VacacionesRepository vacacionesRJPA, ReglasDiasRepository reglasDiasRJPA) {
		this.filesProces = fileProces;
		this.empleadoService = empleadoService;
		this.trunosRJPA = trunosRJPA;
		this.vacacionesRJPA = vacacionesRJPA;
		this.reglasDiasRJPA = reglasDiasRJPA;
	}

	@Override
	public void run() {
		try {
			OPCPackage pkg = null;
			XSSFWorkbook xssfWorkbook = null;
			XSSFSheet xssfSheet = null;
			pkg = OPCPackage.open(this.filesProces);
			try {
				List<ReglasDiasEntity> LReglasDias = reglasDiasRJPA.findAll();
				LocalDate hoy = LocalDate.now();
				xssfWorkbook = new XSSFWorkbook(pkg);
				for (int h = 0; h < xssfWorkbook.getNumberOfSheets(); h++) {
					xssfSheet = xssfWorkbook.getSheetAt(h);
					XSSFRow xssfRow;
					int rows = xssfSheet.getLastRowNum();
					if (xssfSheet.getSheetName().equals("Empleados")) {
						for (int r = 1; r <= rows; r++) {
							xssfRow = xssfSheet.getRow(r);
							if (isRowEmpty(xssfRow)) {
								break;
							} else {
								TurnosEntity turno = null;
								turno = trunosRJPA.findByDescripcion(xssfRow.getCell(9).getStringCellValue().trim());
								if (turno == null) {
									turno = TurnosEntity.builder().descripcion(xssfRow.getCell(9).getStringCellValue().trim())
											.build();
									trunosRJPA.save(turno);
								}
								String fechaIngreso;
								Date fechaIngresoD = null;
								WorkDates workD = new WorkDates();
								LocalDate localDate = null;
								try {
									fechaIngreso = xssfRow.getCell(7).getStringCellValue();
									String[] splitfechas = fechaIngreso.split("DE");
									DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
									localDate = LocalDate.parse(splitfechas[0].trim() + "/"
											+ workD.getMM(splitfechas[1].trim()) + "/" + splitfechas[2].trim(),
											formatter);
									Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
									fechaIngresoD = Date.from(instant);
								} catch (Exception err) {
									fechaIngresoD = xssfRow.getCell(7).getDateCellValue();
								}
								Empleados empleado = Empleados.builder()
										.NEmpleado((long) xssfRow.getCell(2).getNumericCellValue())
										.nombre(xssfRow.getCell(4).getStringCellValue())
										.apellidop(xssfRow.getCell(5).getStringCellValue())
										.apellidom(xssfRow.getCell(6).getStringCellValue()).fechaIngreso(fechaIngresoD)
										.fechaNacimiento(xssfRow.getCell(11).getDateCellValue())
										.sexo(xssfRow.getCell(12).getStringCellValue())
										.correo(xssfRow.getCell(13).getStringCellValue())
										.telefono(Long.toString((long) xssfRow.getCell(14).getNumericCellValue()))
										.salario(BigDecimal.valueOf(xssfRow.getCell(15).getNumericCellValue()))
										.turnosEntity(turno)
										.edad((int)xssfRow.getCell(10).getNumericCellValue()).build();
								this.empleadoService.save(empleado);
								Period periodo = Period.between(localDate, hoy);
								if (periodo.getMonths() < 6) {
									VacacionesEntity vacaciones = VacacionesEntity.builder().diasDisfrutados(0)
											.diasVacaciones(0).diasRestantes(0).empleadoV(empleado).build();
									this.vacacionesRJPA.save(vacaciones);
								} else if (periodo.getMonths() >= 6 && periodo.getMonths() < 12) {
									VacacionesEntity vacaciones = VacacionesEntity.builder().diasDisfrutados(0)
											.diasVacaciones(10).diasRestantes(10).empleadoV(empleado).build();
									this.vacacionesRJPA.save(vacaciones);
								} else {
									for (ReglasDiasEntity RD : LReglasDias) {
										if (periodo.getYears() >= RD.getDesde() && periodo.getYears() <= RD.getAsta()) {
											VacacionesEntity vacaciones = VacacionesEntity.builder().diasDisfrutados(0)
													.diasVacaciones(RD.getDias()).diasRestantes(RD.getDias())
													.empleadoV(empleado).build();
											this.vacacionesRJPA.save(vacaciones);
										}
									}
								}
							}
						}
						System.gc();
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
				throw new Exception(err);
			} finally {
				try {
					pkg.close();
					xssfWorkbook.close();
					this.filesProces.delete();
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new Exception(ex);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		System.gc();
	}

	private boolean isRowEmpty(XSSFRow row) {
		if (row == null) {
			return true; // Considera null como fila vacía
		}
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			XSSFCell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				// Si encuentra una celda que no es nula y no está en blanco, la fila no está
				// vacía
				return false;
			}
		}
		// Si todas las celdas son nulas o están en blanco, la fila está vacía
		return true;
	}

	private String agregaCeros(int cuantos, int leng, String valor) {
		int faltan = cuantos - leng;
		for (int c = 0; c <= faltan; c++) {
			valor = "0" + valor;
		}
		return valor;
	}
}
