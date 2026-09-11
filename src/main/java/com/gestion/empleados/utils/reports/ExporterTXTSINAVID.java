package com.gestion.empleados.utils.reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenasEntity;

public class ExporterTXTSINAVID{
	private List<Empleados> lempleados;
	private String nameFile,quinCatSelect;
	public ExporterTXTSINAVID(String nameFile ,List<Empleados> lempleados,String quinCatSelect) {
		this.lempleados = lempleados;
		this.nameFile=nameFile;
		this.quinCatSelect=quinCatSelect;
	}
	public void runAltaCuentas() throws Exception{
		try {
		       try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.nameFile))) {
		    	   for(Empleados emp:lempleados) {
		    		   System.out.println(emp.getId());
						QuincenasEntity quincenaBuscada = emp.getQuincenas().stream()
							    .filter(q -> q.getQuinCat() != null && q.getQuinCat().stream()
							        .anyMatch(cat -> cat.getIdQNA() != null && cat.getIdQNA().equals(quinCatSelect)))
							    .findFirst()
							    .orElse(null);
		            // Escribir cabecera
		            writer.write("A"+ "\t" +emp.getCurp() + "\t" + emp.getRfc() + "\t" + emp.getApellidop() + "\t" + emp.getApellidom() + "\t" + emp.getNombre()
		            + "\t" + emp.getDomicilios().getCalle()+ "\t" + emp.getDomicilios().getNumExt()+ "\t" + emp.getDomicilios().getNumInt()+ "\t" + emp.getDomicilios().getColonia()+ "\t" + emp.getDomicilios().getCp()
		            + "\t"+"637"+ "\t"+"50020" + "\t" + emp.getFechaIngresoH()+ "\t"+"30"+ "\t"+"4959" + "\t"+quincenaBuscada.getSueldoBase() + "\t"+quincenaBuscada.getSueldoBase() + "\t"+quincenaBuscada.getSueldoBase());
		            writer.newLine(); // Salto de línea
		            System.out.println("Archivo creado con éxito.");
		    	   }
		        } catch (IOException e) {
		            System.out.println("Ocurrió un error al escribir el archivo.");
		            e.printStackTrace();
		        }
		}catch(Exception err) {
			err.printStackTrace();
			throw new Exception(err);
		}
	}
}
