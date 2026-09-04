package com.gestion.empleados.utils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.math3.analysis.function.Add;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gestion.empleados.entity.BancosEntity;
import com.gestion.empleados.entity.CatCPJALEntity;
import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.DeduccionesEntity;
import com.gestion.empleados.entity.DetalleDeduccionesEntiy;
import com.gestion.empleados.entity.DetallePersepcionesEntity;
import com.gestion.empleados.entity.DomiciliosEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.PersepcionesEntity;
import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.entity.SINAVIDEntity;
import com.gestion.empleados.entity.ServiciosEntity;
import com.gestion.empleados.entity.TurnosEntity;
import com.gestion.empleados.entity.VacacionesEntity;
import com.gestion.empleados.repository.BancosRepositoryJPA;
import com.gestion.empleados.repository.CatCPJALRepositoryJPA;
import com.gestion.empleados.repository.CuentasRepositoryJPA;
import com.gestion.empleados.repository.CuentasRepositoryJPA;
import com.gestion.empleados.repository.DeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetalleDeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetallePersepcionesRepositoryJPA;
import com.gestion.empleados.repository.DomicilioRepositoryJPA;
import com.gestion.empleados.repository.EmpleadosRepositoryJPA;
import com.gestion.empleados.repository.PersepcionesRepositoryJPA;
import com.gestion.empleados.repository.PuestosRepositoryJPA;
import com.gestion.empleados.repository.QuincenaRepositoryJPA;
import com.gestion.empleados.repository.QuincenasCatRepositoryJPA;
import com.gestion.empleados.repository.ReglasDiasRepository;
import com.gestion.empleados.repository.SINAVIDRepositoryJPA;
import com.gestion.empleados.repository.ServiciosRepositoryJPA;
import com.gestion.empleados.repository.TurnosRepositoryJPA;
import com.gestion.empleados.repository.VacacionesRepository;
import com.gestion.empleados.service.EmpleadoService;

import jakarta.transaction.Transactional;

import java.math.RoundingMode;

@Service
public class ProcesaFileXLSXThread {
	@Autowired
	private EmpleadosRepositoryJPA empleadosJPA;
	@Autowired
	private TurnosRepositoryJPA trunosJPA;
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
	@Autowired
	private ProcesServiceTransactional procesTransactional;
	@Autowired
	private CuentasRepositoryJPA cuentasJPA;
	@Autowired
	private DomicilioRepositoryJPA domiJPA;
	@Autowired
	private SINAVIDRepositoryJPA sinavidJPA;

	@Async
	public void run(File fileProces, String tipoCarga, String quinString) {

		try {
			OPCPackage pkg = null;
			Workbook Workbook = null;
			Sheet Sheet = null;
			try {
				LocalDate hoy = LocalDate.now();
				Workbook = WorkbookFactory.create(fileProces);
				for (int h = 0; h < Workbook.getNumberOfSheets(); h++) {
					Sheet = Workbook.getSheetAt(h);
					Row Row;
					int rows = Sheet.getLastRowNum();
					if (tipoCarga.equals("CBA")) {
						if (Sheet.getSheetName().equals("BANCOS")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									BancosEntity banco = null;
									banco = this.bancosJPA.findByBanco(Row.getCell(0).getStringCellValue().trim());
									if (banco == null) {
										banco = BancosEntity.builder().banco(Row.getCell(0).getStringCellValue().trim())
												.nombre(Row.getCell(1).getStringCellValue().trim()).build();
										this.bancosJPA.save(banco);
										this.bancosJPA.saveAndFlush(banco);
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CCU")) {

					}
					if (tipoCarga.equals("CPU")) {
						if (Sheet.getSheetName().equals("PUESTOS")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									PuestosEntity puesto = null;
									System.out.println(Row.getCell(0).getStringCellValue().trim());
									puesto = this.puestosJPA.findByPuesto(Row.getCell(0).getStringCellValue().trim());
									if (puesto == null) {
										puesto = PuestosEntity.builder()
												.puesto(Row.getCell(0).getStringCellValue().trim()).build();
										this.puestosJPA.save(puesto);
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CSE")) {
						if (Sheet.getSheetName().equals("SERVICIOS")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									ServiciosEntity servicio = null;
									servicio = this.serviciosJPA
											.findByServicio(Row.getCell(0).getStringCellValue().trim());
									if (servicio == null) {
										servicio = ServiciosEntity.builder()
												.servicio(Row.getCell(0).getStringCellValue().trim()).build();
										this.serviciosJPA.save(servicio);
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CPER")) {
						if (Sheet.getSheetName().equals("PERSEPCIONES")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									PersepcionesEntity persepcion = null;
									String clave = "";
									try {
										clave = Row.getCell(0).getStringCellValue().trim();
									} catch (Exception err) {
										clave = NumberToTextConverter.toText(Row.getCell(0).getNumericCellValue());
									}
									persepcion = this.persepcionesJPA.findByClaveAndDescripcion(clave,
											Row.getCell(1).getStringCellValue().trim());
									if (persepcion == null) {
										persepcion = PersepcionesEntity.builder().clave(clave)
												.descripcion(Row.getCell(1).getStringCellValue().trim()).build();
										this.persepcionesJPA.save(persepcion);
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CDED")) {
						if (Sheet.getSheetName().equals("DEDUCCIONES")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									DeduccionesEntity deduccion = null;
									String clave = "";
									try {
										clave = Row.getCell(0).getStringCellValue().trim();
									} catch (Exception err) {
										clave = NumberToTextConverter.toText(Row.getCell(0).getNumericCellValue());
									}
									deduccion = this.deduccionesJPA.findByClaveAndDescripcion(clave,
											Row.getCell(1).getStringCellValue().trim());
									if (deduccion == null) {
										deduccion = DeduccionesEntity.builder().clave(clave)
												.descripcion(Row.getCell(1).getStringCellValue().trim()).build();
										this.deduccionesJPA.save(deduccion);
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CQNA")) {
						if (Sheet.getSheetName().equals("QUINCENAS")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									QuincenaCatEntity quincena = null;
									quincena = this.quincenasCatJPA
											.findByIdQNA(Row.getCell(0).getStringCellValue().trim());
									if (quincena == null) {
										quincena = QuincenaCatEntity.builder()
												.idQNA(Row.getCell(0).getStringCellValue().trim())
												.fechaInicio(
														new java.sql.Date(Row.getCell(1).getDateCellValue().getTime()))
												.fechaFin(
														new java.sql.Date(Row.getCell(2).getDateCellValue().getTime()))
												.fechaPago(
														new java.sql.Date(Row.getCell(3).getDateCellValue().getTime()))
												.fechaCheque(
														new java.sql.Date(Row.getCell(4).getDateCellValue().getTime()))
												.fechaSuspencion(
														new java.sql.Date(Row.getCell(5).getDateCellValue().getTime()))
												.build();
									} else {
										quincena.setFechaInicio(
												new java.sql.Date(Row.getCell(1).getDateCellValue().getTime()));
										quincena.setFechaFin(
												new java.sql.Date(Row.getCell(2).getDateCellValue().getTime()));
										quincena.setFechaPago(
												new java.sql.Date(Row.getCell(3).getDateCellValue().getTime()));
										quincena.setFechaCheque(
												new java.sql.Date(Row.getCell(4).getDateCellValue().getTime()));
										quincena.setFechaSuspencion(
												new java.sql.Date(Row.getCell(5).getDateCellValue().getTime()));
									}
									this.quincenasCatJPA.save(quincena);
								}
							}
						}
					}
					if (tipoCarga.equals("CPRE")) {
						Empleados emp = new Empleados();
						PersepcionesEntity per = null;
						DeduccionesEntity ded = null;
						DetallePersepcionesEntity detPer = null;
						DetalleDeduccionesEntiy detded = null;
						QuincenasEntity quin = null;
						QuincenaCatEntity quinCat = this.quincenasCatJPA.findByIdQNA(quinString);
						for (int r = 0; r <= rows; r++) {
							Row = Sheet.getRow(r);
							if (Row != null) {

								if (Row.getCell(0) != null) {
									System.out.println(Row.getCell(0).getStringCellValue());
									if (Row.getCell(0).getCellType() != CellType.BLANK) {
										if (Row.getCell(0).getCellType() == CellType.STRING) {
											try {
												Long IDEMPLEADO = Long.parseLong(Row.getCell(0).getStringCellValue());
												emp = this.empleadosJPA.findById(IDEMPLEADO);
												if (emp == null) {
													emp = new Empleados();
													emp.setId(IDEMPLEADO);
													emp.setNombreCompleto(Row.getCell(1).getStringCellValue());
													String[] valores = emp.getNombreCompleto().split(" ");
													emp.setApellidop(valores[0]);
													emp.setApellidom(valores[1]);
													emp.setNombre(String.join(" ",
															Arrays.copyOfRange(valores, 2, valores.length)));
													emp.setActivo(true);
													emp.setPuestosEntity(this.puestosJPA.findById(0).get());
													emp.setServicioEntity(this.serviciosJPA.findById(0).get());
													emp.isNew();
//												this.empleadosJPA.save(emp);
//												saveTransactional(emp);
													try {
														procesTransactional.saveEmpleado(emp);
													} catch (Exception err) {
														err.printStackTrace();
													}
												}
												quin = new QuincenasEntity();
											} catch (Throwable e) {
											}
											if (Row.getCell(0).getStringCellValue().contains("P")) {
												String valor = Row.getCell(0).getStringCellValue();
												if (valor.contains("P 01")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("01");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 01") + 4,
																			valor.indexOf("P 01") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 02")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("02");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 02") + 4,
																			valor.indexOf("P 02") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 04")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("04");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 04") + 4,
																			valor.indexOf("P 04") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 05")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("05");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 05") + 4,
																			valor.indexOf("P 05") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 06")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("06");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 06") + 4,
																			valor.indexOf("P 06") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 07")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("07");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 07") + 4,
																			valor.indexOf("P 07") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 08")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("08");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 08") + 4,
																			valor.indexOf("P 08") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 10")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("10");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 10") + 4,
																			valor.indexOf("P 10") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P B2")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("B2");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P B2") + 4,
																			valor.indexOf("P B2") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 33")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("33");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 33") + 4,
																			valor.indexOf("P 33") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 35")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("35");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 35") + 4,
																			valor.indexOf("P 35") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
												if (valor.contains("P 48")) {
													detPer = new DetallePersepcionesEntity();
													per = new PersepcionesEntity();
													per = this.persepcionesJPA.findByClave("48");
													detPer.setPersepciones(per);
													detPer.setImporte(
															new BigDecimal(valor
																	.substring(valor.indexOf("P 48") + 4,
																			valor.indexOf("P 48") + 16)
																	.trim().replace(",", ""))
																	.setScale(2, RoundingMode.HALF_UP));
													detPer.setQuincenaCatDP(quinCat);
													detPer.setEmpleadoPer(emp);
													this.detallePerJPA.save(detPer);
												}
											}
											if (Row.getCell(0).getStringCellValue().contains("Fecha de Revisión")) {
												emp.setPlaza(NumberToTextConverter
														.toText(Row.getCell(4).getNumericCellValue()));
												Date fecha = DateUtil.getJavaDate(Row.getCell(5).getNumericCellValue());
												emp.setFechaIngreso(fecha);
												emp.setClaveP(Row.getCell(7).getStringCellValue());
												this.empleadosJPA.save(emp);
											}

											if (Row.getCell(0).getStringCellValue().contains("Total")) {
												quin.setSueldoPre(
														BigDecimal.valueOf(Row.getCell(5).getNumericCellValue())
																.setScale(2, RoundingMode.HALF_UP));
												quin.setFolioPre(Row.getCell(7).getStringCellValue());
											}
										}
										if (quin != null && quin.getSueldoPre() != null) {
											ArrayList<BigDecimal> sumSueldoBase = new ArrayList<BigDecimal>();
											ArrayList<BigDecimal> sumSueldoQN = new ArrayList<BigDecimal>();
											List<DetallePersepcionesEntity> detper = this.detallePerJPA
													.findByEmpleadoPerAndQuincenaCatDP(emp, quinCat);
											for (DetallePersepcionesEntity d : detper) {
												if (d.getPersepciones().getClave().equals("01")) {
													sumSueldoBase.add(d.getImporte());
													sumSueldoQN.add(d.getImporte());
												}
												if (d.getPersepciones().getClave().equals("04"))
													sumSueldoBase.add(d.getImporte());
												if (d.getPersepciones().getClave().equals("05"))
													sumSueldoBase.add(d.getImporte());
												if (d.getPersepciones().getClave().equals("06"))
													sumSueldoBase.add(d.getImporte());
												if (d.getPersepciones().getClave().equals("07"))
													sumSueldoBase.add(d.getImporte());
												if (d.getPersepciones().getClave().equals("08"))
													sumSueldoBase.add(d.getImporte());
												if (d.getPersepciones().getClave().equals("B2"))
													sumSueldoBase.add(d.getImporte());
												if (d.getPersepciones().getClave().equals("10"))
													sumSueldoQN.add(d.getImporte());

											}
											quin.setSueldoBase(sumSueldoBase.stream().filter(Objects::nonNull)
													.reduce(BigDecimal.ZERO, BigDecimal::add)
													.multiply(BigDecimal.valueOf(2.00))
													.setScale(2, RoundingMode.HALF_UP));

											List<DetalleDeduccionesEntiy> detdedu = this.detalleDedJPA
													.findByEmpleadoDDAndQuincenaCatDD(emp, quinCat);
											for (DetalleDeduccionesEntiy d : detdedu) {
												if (d.getDeducciones().getClave().equals("S7"))
													sumSueldoQN.add(d.getImporte());
												if (d.getDeducciones().getClave().equals("S8"))
													sumSueldoQN.add(d.getImporte());
												if (d.getDeducciones().getClave().equals("4S"))
													sumSueldoQN.add(d.getImporte());
											}
											quin.setSalarioQN(sumSueldoQN.stream().filter(Objects::nonNull)
													.reduce(BigDecimal.ZERO, BigDecimal::add)
													.setScale(2, RoundingMode.HALF_UP));
											quin.setSalarioMen(quin.getSalarioQN().multiply(BigDecimal.valueOf(2.00))
													.setScale(2, RoundingMode.HALF_UP));
											quin.setSalarioAn(quin.getSalarioQN().multiply(BigDecimal.valueOf(24.00))
													.setScale(2, RoundingMode.HALF_UP));
											quin.setFechaRegistro(new Date());
											List<Empleados> lempleados = new ArrayList<>();
											lempleados.add(emp);
											quin.setEmpleadoQN(lempleados);
											Set<QuincenaCatEntity> lquiCat = new HashSet<>();
											lquiCat.add(quinCat);
											quin.setQuinCat(lquiCat);
											quin.setTipoPago("TEMPORAL");
											quincenaJPA.save(quin);
											quin = null;
										}
									}
								}
								if (Row.getCell(5) != null && Row.getCell(5).getCellType() == CellType.STRING) {
									if (Row.getCell(5).getStringCellValue().contains("D")) {
										String valor = Row.getCell(5).getStringCellValue();
										System.out.println(valor);
										if (valor.contains("D 53")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClaveAndDescripcion("53",
													"Impuesto sobre la Renta");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 53") + 4, valor.indexOf("D 53") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D S7")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("S7");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D S7") + 4, valor.indexOf("D S7") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D S8")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("S8");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D S8") + 4, valor.indexOf("D S8") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 4S")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("4S");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 4S") + 4, valor.indexOf("D 4S") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 60")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("60");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 60") + 4, valor.indexOf("D 60") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 62")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("62");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 62") + 4, valor.indexOf("D 62") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 65")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("65");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 65") + 4, valor.indexOf("D 65") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 66")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("66");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 66") + 4, valor.indexOf("D 66") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 86")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("86");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 86") + 4, valor.indexOf("D 86") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D S9")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("S9");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D S9") + 4, valor.indexOf("D S9") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D 1S")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("1S");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D 1S") + 4, valor.indexOf("D 1S") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
										if (valor.contains("D CF")) {
											detded = new DetalleDeduccionesEntiy();
											ded = new DeduccionesEntity();
											ded = this.deduccionesJPA.findByClave("CF");
											detded.setImporte(new BigDecimal(valor
													.substring(valor.indexOf("D CF") + 4, valor.indexOf("D CF") + 16)
													.trim().replace(",", "")).setScale(2, RoundingMode.HALF_UP));
											detded.setDeducciones(ded);
											detded.setEmpleadoDD(emp);
											detded.setQuincenaCatDD(quinCat);
											this.detalleDedJPA.save(detded);
										}
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CNOM")) {
						QuincenaCatEntity quinCat = this.quincenasCatJPA.findByIdQNA(quinString);
						Empleados emp;
						for (int r = 1; r <= rows; r++) {
							Row = Sheet.getRow(r);
							if (Row != null) {
								if (Row.getCell(0) != null) {
									if (Row.getCell(0).getCellType() != CellType.BLANK) {
										try {
											System.out.println((long) Row.getCell(0).getNumericCellValue());
											Long IDEMPLEADO = (long) Row.getCell(0).getNumericCellValue();
											emp = this.empleadosJPA.findById(IDEMPLEADO);
											if(emp!=null) {
												QuincenasEntity quin=this.quincenaJPA.findByQuinCatAndEmpleadoQN_Id(quinCat, emp.getId());
												quin.setSueldoNeto(Row.getCell(4).getCellType()==CellType.NUMERIC?new BigDecimal(Row.getCell(4).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP):new BigDecimal(Row.getCell(4).getStringCellValue()).setScale(2, RoundingMode.HALF_UP));
												quin.setTipoPago(Row.getCell(5).getStringCellValue());
												quin.setFolioQuin(Row.getCell(6).getStringCellValue());
												this.quincenaJPA.save(quin);
											}
//											if(emp==null)
//											{
//												emp = new Empleados();
//												emp.setId(IDEMPLEADO);
//												emp.setNombreCompleto(Row.getCell(1).getStringCellValue());
//												String[] valores = emp.getNombreCompleto().split(" ");
//												emp.setApellidop(valores[0]);
//												emp.setApellidom(valores[1]);
//												emp.setNombre(String.join(" ",
//														Arrays.copyOfRange(valores, 2, valores.length)));
//												emp.setActivo(true);
//												emp.setPuestosEntity(this.puestosJPA.findById(0).get());
//												emp.setServicioEntity(this.serviciosJPA.findById(0).get());
//												emp.isNew();
//												try {
//													procesTransactional.saveEmpleado(emp);
//												} catch (Exception err) {
//													err.printStackTrace();
//												}
//											}
										} catch (Exception err) {
											err.printStackTrace();
										}
									}
								}
							}
						}
					}
					if (tipoCarga.equals("CEM")) {
						Empleados emp;
						PuestosEntity puesto;
						ServiciosEntity servi;
						TurnosEntity turno = new TurnosEntity();
						BancosEntity banco = new BancosEntity();
						CuentasEntity cuenta = new CuentasEntity();
						DomiciliosEntity domi = new DomiciliosEntity();
						SINAVIDEntity sinavid = new SINAVIDEntity();
						for (int r = 2; r <= rows; r++) {
							Row = Sheet.getRow(r);
							if (Row != null) {
								if (Row.getCell(2) != null) {
									if (Row.getCell(2).getCellType() != CellType.BLANK) {
										try {
											System.out.println((long) Row.getCell(2).getNumericCellValue());
											Long IDEMPLEADO = (long) Row.getCell(2).getNumericCellValue();
											emp = this.empleadosJPA.findById(IDEMPLEADO);
											if(emp==null)
											{
												emp = new Empleados();
												emp.setId(IDEMPLEADO);
												emp.setNombreCompleto(Row.getCell(7).getStringCellValue());
												String[] valores = emp.getNombreCompleto().split(" ");
												emp.setApellidop(valores[0]);
												emp.setApellidom(valores[1]);
												emp.setNombre(String.join(" ",
														Arrays.copyOfRange(valores, 2, valores.length)));
												emp.setActivo(true);
												emp.setPuestosEntity(this.puestosJPA.findById(0).get());
												emp.setServicioEntity(this.serviciosJPA.findById(0).get());
												emp.isNew();
												try {
													procesTransactional.saveEmpleado(emp);
												} catch (Exception err) {
													err.printStackTrace();
												}
											}
											if (Row.getCell(9).getCellType() == CellType.NUMERIC) {
												Date fecha = DateUtil.getJavaDate(Row.getCell(9).getNumericCellValue());
												emp.setFechaIngresoH(fecha);
											}
											emp.setTipoContrato(Row.getCell(15).getStringCellValue());
											emp.setCurp(Row.getCell(21).getStringCellValue());
											emp.setRfc(Row.getCell(22).getStringCellValue());
											emp.setTelefono(Row.getCell(36).getCellType() == CellType.NUMERIC
													? Integer.toString((int) Row.getCell(36).getNumericCellValue())
													: Row.getCell(36).getStringCellValue());
											puesto = puestosJPA.findByPuesto(Row.getCell(32).getStringCellValue());
											emp.setPuestosEntity(puesto);
											servi = serviciosJPA.findByServicio(Row.getCell(33).getStringCellValue());
											emp.setServicioEntity(servi);
											this.empleadosJPA.save(emp);
											turno = this.trunosJPA.findByTurno(Row.getCell(34).getStringCellValue());
											if (turno == null) {
												turno = TurnosEntity.builder()
														.turno(Row.getCell(34).getStringCellValue())
														.horario(Row.getCell(35).getStringCellValue()).empleado(emp)
														.build();
												this.trunosJPA.save(turno);
											}
											banco = this.bancosJPA.findByBanco(Row.getCell(30).getStringCellValue());
											if (banco == null) {
												banco = BancosEntity.builder()
														.banco(Row.getCell(30).getStringCellValue())
														.nombre(Row.getCell(30).getStringCellValue()).build();
												this.bancosJPA.save(banco);
											}
											cuenta = this.cuentasJPA.findByCuenta(Row.getCell(31).getStringCellValue());
											if (cuenta == null) {
												cuenta = CuentasEntity.builder().bancoE(banco).empleadoC(emp)
														.cuenta(Row.getCell(31).getStringCellValue()).estatus("ALTA")
														.alta(Row.getCell(29).getCellType()==CellType.NUMERIC?String.valueOf((int)Row.getCell(29).getNumericCellValue()): Row.getCell(29).getStringCellValue()).build();
												this.cuentasJPA.save(cuenta);
											}
											if (!Row.getCell(23).getStringCellValue().equals("No Encontrado")) {
												domi = DomiciliosEntity.builder()
														.calle(Row.getCell(23).getStringCellValue())
														.numExt(Row.getCell(24).getCellType() == CellType.NUMERIC
																? String.valueOf(
																		(int) Row.getCell(24).getNumericCellValue())
																: Row.getCell(24).getStringCellValue())
														.numInt(Row.getCell(25) != null
																? Row.getCell(25).getCellType() == CellType.NUMERIC
																		? String.valueOf((int) Row.getCell(25)
																				.getNumericCellValue())
																		: Row.getCell(25).getStringCellValue()
																: "")
														.colonia(Row.getCell(26) != null
																? Row.getCell(26).getStringCellValue()
																: "")
														.cp(Row.getCell(27).getCellType() == CellType.NUMERIC
																? String.valueOf(
																		(int) Row.getCell(27).getNumericCellValue())
																: Row.getCell(27).getStringCellValue())
														.build();
												domi.setDomiEm(emp);
												this.domiJPA.save(domi);
											}
											if (Row.getCell(20).getCellType() == CellType.NUMERIC) {
												sinavid = this.sinavidJPA.findByNumISSSTE(Row.getCell(38)!=null?
														Row.getCell(38).getCellType() == CellType.NUMERIC
																? String.valueOf(
																		(long) Row.getCell(38).getNumericCellValue())
																: Row.getCell(38).getStringCellValue():"");
												if (sinavid == null) {
													sinavid = SINAVIDEntity.builder()
															.pagaduria(String.valueOf(
																	(int) Row.getCell(20).getNumericCellValue()))
															.estatus(Row.getCell(19).getStringCellValue())
															.alta(Row.getCell(17).getStringCellValue())
															.fechaRegistro(new Date())
															.nss(Row.getCell(39)!=null?Row.getCell(39).getCellType() == CellType.NUMERIC
																	? String.valueOf(
																			(int) Row.getCell(39).getNumericCellValue())
																	: Row.getCell(39).getStringCellValue():"")
															.numISSSTE(Row.getCell(38)!=null?Row.getCell(38).getCellType() == CellType.NUMERIC
																	? String.valueOf((long) Row.getCell(38)
																			.getNumericCellValue())
																	: Row.getCell(38).getStringCellValue():"")
															.sueldoSINAVID(BigDecimal
																	.valueOf(Row.getCell(40).getNumericCellValue())
																	.setScale(2, RoundingMode.HALF_UP))
															.sueldoSAR(BigDecimal
																	.valueOf(Row.getCell(41).getNumericCellValue())
																	.setScale(2, RoundingMode.HALF_UP))
															.remTotal(BigDecimal
																	.valueOf(Row.getCell(42).getNumericCellValue())
																	.setScale(2, RoundingMode.HALF_UP))
															.sinavidEm(emp).build();
													this.sinavidJPA.save(sinavid);
												}
											}
										} catch (Exception err) {
											err.printStackTrace();
										}
									}
								}
							}
						}
//					if (Sheet.getSheetName().equals("Empleados")) {
//						List<ReglasDiasEntity> LReglasDias = reglasDiasRJPA.findAll();
//						ArrayList<TurnosEntity> ALTurnos = new ArrayList<>();
//						for (int r = 1; r <= rows; r++) {
//							Row = Sheet.getRow(r);
//							if (Row == null) {
//								break;
//							} else {
//								TurnosEntity turno = null;
//								turno = trunosRJPA.findByTurno(Row.getCell(9).getStringCellValue().trim());
//								if (turno == null) {
//									turno = TurnosEntity.builder().turno(Row.getCell(9).getStringCellValue().trim())
//											.build();
//									trunosRJPA.save(turno);
//									ALTurnos.add(turno);
//								}
//								String fechaIngreso;
//								Date fechaIngresoD = null;
//								WorkDates workD = new WorkDates();
//								LocalDate localDate = null;
//								try {
//									fechaIngreso = Row.getCell(7).getStringCellValue();
//									String[] splitfechas = fechaIngreso.split("DE");
//									DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//									localDate = LocalDate.parse(splitfechas[0].trim() + "/"
//											+ workD.getMM(splitfechas[1].trim()) + "/" + splitfechas[2].trim(),
//											formatter);
//									Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//									fechaIngresoD = Date.from(instant);
//								} catch (Exception err) {
//									fechaIngresoD = Row.getCell(7).getDateCellValue();
//								}
//								Empleados empleado = Empleados.builder()
//										.id((long) Row.getCell(2).getNumericCellValue())
//										.nombre(Row.getCell(4).getStringCellValue())
//										.apellidop(Row.getCell(5).getStringCellValue())
//										.apellidom(Row.getCell(6).getStringCellValue()).fechaIngreso(fechaIngresoD)
//										.fechaNacimiento(Row.getCell(11).getDateCellValue())
//										.sexo(Row.getCell(12).getStringCellValue())
//										.correo(Row.getCell(13).getStringCellValue())
//										.telefono(Long.toString((long) Row.getCell(14).getNumericCellValue()))
////									.sueldoNeto(BigDecimal.valueOf(Row.getCell(15).getNumericCellValue()))
//										.turnosEm(ALTurnos)
////										.edad((int) Row.getCell(10).getNumericCellValue())
//										.build();
//								this.empleadosJPA.save(empleado);
//								Period periodo = Period.between(localDate, hoy);
//								if (periodo.getMonths() < 6) {
//									VacacionesEntity vacaciones = VacacionesEntity.builder().diasDisfrutados(0)
//											.diasVacaciones(0).diasRestantes(0).empleadoV(empleado).build();
//									this.vacacionesRJPA.save(vacaciones);
//								} else if (periodo.getMonths() >= 6 && periodo.getMonths() < 12) {
//									VacacionesEntity vacaciones = VacacionesEntity.builder().diasDisfrutados(0)
//											.diasVacaciones(10).diasRestantes(10).empleadoV(empleado).build();
//									this.vacacionesRJPA.save(vacaciones);
//								} else {
//									for (ReglasDiasEntity RD : LReglasDias) {
//										if (periodo.getYears() >= RD.getDesde()
//												&& periodo.getYears() <= RD.getAsta()) {
//											VacacionesEntity vacaciones = VacacionesEntity.builder()
//													.diasDisfrutados(0).diasVacaciones(RD.getDias())
//													.diasRestantes(RD.getDias()).empleadoV(empleado).build();
//											this.vacacionesRJPA.save(vacaciones);
//										}
//									}
//								}
//							}
//						}
//					}
						System.gc();
					}

					if (tipoCarga.equals("CCP")) {
						if (Sheet.getSheetName().equals("CPJAL")) {
							for (int r = 0; r <= rows; r++) {
								Row = Sheet.getRow(r);
								if (Row == null) {
									break;
								} else {
									CatCPJALEntity cpj = null;
									cpj = this.catCPJALRepositoryJPA.findByCpAndColonia(
											Row.getCell(1).getStringCellValue().trim(),
											Row.getCell(0).getStringCellValue().trim());
									if (cpj == null) {
										cpj = CatCPJALEntity.builder()
												.colonia(Row.getCell(0).getStringCellValue().trim())
												.cp(Row.getCell(1).getStringCellValue().trim()).build();
										this.catCPJALRepositoryJPA.save(cpj);
									}
								}
							}
						}
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
				throw new Exception(err);
			} finally {
				try {
//				pkg.close();
					Workbook.close();
					fileProces.delete();
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
//	private boolean isRowEmpty(XSSFRow row) {
//		if (row == null) {
//			return true; // Considera null como fila vacía
//		}
//		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
//			XSSFCell cell = row.getCell(i);
//			if (cell != null && cell.getCellType() != CellType.BLANK) {
//				// Si encuentra una celda que no es nula y no está en blanco, la fila no está
//				// vacía
//				return false;
//			}
//		}
//		// Si todas las celdas son nulas o están en blanco, la fila está vacía
//		return true;
//	}

	private String agregaCeros(int cuantos, int leng, String valor) {
		int faltan = cuantos - leng;
		for (int c = 0; c <= faltan; c++) {
			valor = "0" + valor;
		}
		return valor;
	}
}