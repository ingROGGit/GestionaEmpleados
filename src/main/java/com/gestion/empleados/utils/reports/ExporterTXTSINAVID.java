package com.gestion.empleados.utils.reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenasEntity;

public class ExporterTXTSINAVID {
	private List<Empleados> lempleados;
	private String nameFile, quinCatSelect;

	public ExporterTXTSINAVID(String nameFile, List<Empleados> lempleados, String quinCatSelect) {
		this.lempleados = lempleados;
		this.nameFile = nameFile;
		this.quinCatSelect = quinCatSelect;
	}

	public void runAltaCuentas() throws Exception {
		try {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.nameFile))) {
				for (Empleados emp : lempleados) {
					System.out.println(emp.getId());
					QuincenasEntity quincenaBuscada = emp.getQuincenas().stream()
							.filter(q -> q.getQuinCat() != null && q.getQuinCat().stream()
									.anyMatch(cat -> cat.getIdQNA() != null && cat.getIdQNA().equals(quinCatSelect)))
							.findFirst().orElse(null);
					// Escribir cabecera
					if (quincenaBuscada != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						System.out.println("A" + "\t" + emp.getCurp().trim() + "\t" + emp.getRfc().trim() + "\t" + emp.getApellidop().trim() + "\t"
								+ emp.getApellidom().trim() + "\t" + emp.getNombre().trim() + "\t" + emp.getDomicilios().getCalle().trim()
								+ "\t" + emp.getDomicilios().getNumExt() + "\t" + emp.getDomicilios().getNumInt() + "\t"
								+ emp.getDomicilios().getColonia().trim() + "\t" + emp.getDomicilios().getCp().trim() + "\t" + "637"
								+ "\t" + "50020" + "\t" + sdf.format(emp.getFechaIngreso()) + "\t" + "30" + "\t" + "4959" + "\t"
								+ quincenaBuscada.getSueldoBase() + "\t" + quincenaBuscada.getSueldoBase() + "\t"
								+ quincenaBuscada.getSueldoBase());
						writer.write("A" + "\t" + emp.getCurp().trim() + "\t" + emp.getRfc().trim() + "\t" + emp.getApellidop().trim() + "\t"
								+ emp.getApellidom().trim() + "\t" + emp.getNombre().trim() + "\t" + emp.getDomicilios().getCalle().trim()
								+ "\t" + emp.getDomicilios().getNumExt() + "\t" + emp.getDomicilios().getNumInt() + "\t"
								+ emp.getDomicilios().getColonia().trim() + "\t" + emp.getDomicilios().getCp().trim() + "\t" + "637"
								+ "\t" + "50020" + "\t" + sdf.format(emp.getFechaIngreso()) + "\t" + "30" + "\t" + "4959" + "\t"
								+ quincenaBuscada.getSueldoBase() + "\t" + quincenaBuscada.getSueldoBase() + "\t"
								+ quincenaBuscada.getSueldoBase());
						writer.newLine(); // Salto de línea
						
					}
				}
			} catch (IOException e) {
				System.out.println("Ocurrió un error al escribir el archivo.");
				e.printStackTrace();
			}
		} catch (Exception err) {
			err.printStackTrace();
			throw new Exception(err);
		}
	}
	public void runModSalario(Date fechaIni) throws Exception {
		try {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.nameFile))) {
				for (Empleados emp : lempleados) {
					System.out.println(emp.getId());
					// Escribir cabecera
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						System.out.println("M" + "\t" + emp.getCurp().trim() + "\t" + emp.getApellidop().trim() + "\t"
								+ emp.getApellidom().trim() + "\t" + emp.getNombre().trim() + "\t" + "637"
								+ "\t" + emp.getSinavid().getPagaduria() + "\t" + sdf.format(fechaIni) + "\t" + "30" + "\t" + "4959" + "\t"
								+ emp.getQuincenas().get(0).getSueldoBase() + "\t" + emp.getQuincenas().get(0).getSueldoBase() + "\t"
								+ emp.getQuincenas().get(0).getSueldoBase());
						writer.write("M" + "\t" + emp.getCurp().trim() + "\t" + emp.getApellidop().trim() + "\t"
								+ emp.getApellidom().trim() + "\t" + emp.getNombre().trim() + "\t" + "637"
								+ "\t" + emp.getSinavid().getPagaduria() + "\t" + sdf.format(fechaIni) + "\t" + "30" + "\t" + "4959" + "\t"
								+ emp.getQuincenas().get(0).getSueldoBase() + "\t" + emp.getQuincenas().get(0).getSueldoBase() + "\t"
								+ emp.getQuincenas().get(0).getSueldoBase());
						writer.newLine(); // Salto de línea
				}
			} catch (IOException e) {
				System.out.println("Ocurrió un error al escribir el archivo.");
				e.printStackTrace();
			}
		} catch (Exception err) {
			err.printStackTrace();
			throw new Exception(err);
		}
	}
	public void runBaja() throws Exception {
		try {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.nameFile))) {
				for (Empleados emp : lempleados) {
					System.out.println(emp.getId());
					// Escribir cabecera
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						System.out.println("B" + "\t" + emp.getCurp().trim() + "\t" + emp.getApellidop().trim() + "\t"
								+ emp.getApellidom().trim() + "\t" + emp.getNombre().trim() + "\t" + "637"
								+ "\t" + emp.getSinavid().getPagaduria() + "\t" + sdf.format(emp.getSinavid().getFechaBaja()) + "\t" + emp.getSinavid().getIdBaja() + "\t"
								+ emp.getSinavid().getSueldoSINAVID() + "\t" + emp.getSinavid().getSueldoSINAVID() + "\t"
								+ emp.getSinavid().getSueldoSINAVID());
						writer.write("B" + "\t" + emp.getCurp().trim() + "\t" + emp.getApellidop().trim() + "\t"
								+ emp.getApellidom().trim() + "\t" + emp.getNombre().trim() + "\t" + "637"
								+ "\t" + emp.getSinavid().getPagaduria() + "\t" + sdf.format(emp.getSinavid().getFechaBaja()) + "\t" + emp.getSinavid().getIdBaja() + "\t"
								+ emp.getSinavid().getSueldoSINAVID() + "\t" + emp.getSinavid().getSueldoSINAVID() + "\t"
								+ emp.getSinavid().getSueldoSINAVID());
						writer.newLine(); // Salto de línea
				}
			} catch (IOException e) {
				System.out.println("Ocurrió un error al escribir el archivo.");
				e.printStackTrace();
			}
		} catch (Exception err) {
			err.printStackTrace();
			throw new Exception(err);
		}
	}
}
