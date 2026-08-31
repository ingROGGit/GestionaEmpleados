package com.gestion.empleados.utils.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.ReglasDiasRepository;

public class ExportExcelThreadCatalogos extends Thread  {
	private String tipo;
	private XSSFWorkbook libro;
	private XSSFSheet sheet;
	private List<Empleados> lempleados;
	private List<UsuariosEntity> lusuarios;
	private List<ReglasDiasEntity> lreglas;
	private String nameFile;
	public ExportExcelThreadCatalogos(String nameFile ,String tipo,List<UsuariosEntity> lusuarios,List<ReglasDiasRepository> lReglas,List<Empleados> lempleados) {
		this.libro = new XSSFWorkbook();
		this.lusuarios = lusuarios;
		this.lreglas = lreglas;
		this.lempleados = lempleados;
		this.nameFile=nameFile;
		this.tipo=tipo;
	}
	@Override
	public void run() {
		try {
			if (this.tipo == "Empleados") {
				this.sheet = this.libro.createSheet("Empleados");
				writeCabTable();
				writeDetalleTabla();
			}
			if (this.tipo == "Reglas") {
				this.sheet = this.libro.createSheet("Reglas");
				writeCabTableReglas();
				writeDetalleReglas();
			}
			if (this.tipo == "Usuarios") {
				this.sheet = this.libro.createSheet("Usuarios");
				writeCabTableUsuarios();
				writeDetalleTablaUsuarios();
			}
			
            // Write the output to a file
            try (FileOutputStream out = new FileOutputStream(this.nameFile)) {
            	this.libro.write(out);
            	this.libro.close();
            	out.close();
            	File exel= new File(this.nameFile);
            	System.out.println(exel.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
	private void writeCabTable() {
		Row row = this.sheet.createRow(0);
		CellStyle estilo = this.libro.createCellStyle();
		XSSFFont fuente = this.libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);

		Cell celda = row.createCell(0);
		celda.setCellValue("Numero");
		celda.setCellStyle(estilo);

		celda = row.createCell(1);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);

		celda = row.createCell(2);
		celda.setCellValue("Apellido P");
		celda.setCellStyle(estilo);

		celda = row.createCell(3);
		celda.setCellValue("Apellido M");
		celda.setCellStyle(estilo);

		celda = row.createCell(4);
		celda.setCellValue("Edad");
		celda.setCellStyle(estilo);

		celda = row.createCell(5);
		celda.setCellValue("Sexo");
		celda.setCellStyle(estilo);

		celda = row.createCell(6);
		celda.setCellValue("Correo");
		celda.setCellStyle(estilo);

		celda = row.createCell(7);
		celda.setCellValue("Telefono");
		celda.setCellStyle(estilo);

		celda = row.createCell(8);
		celda.setCellValue("Salario");
		celda.setCellStyle(estilo);
		
		celda = row.createCell(9);
		celda.setCellValue("Fecha Ingreso");
		celda.setCellStyle(estilo);

		celda = row.createCell(10);
		celda.setCellValue("Años laborados");
		celda.setCellStyle(estilo);
		celda = row.createCell(11);
		celda.setCellValue("Meses laborados");
		celda.setCellStyle(estilo);
		celda = row.createCell(12);
		celda.setCellValue("Dias laborados");
		celda.setCellStyle(estilo);
		celda = row.createCell(13);
		celda.setCellValue("Dias de Vacaciones");
		celda.setCellStyle(estilo);
		
	}

	private void writeCabTableUsuarios() {
		Row row = this.sheet.createRow(0);
		CellStyle estilo = this.libro.createCellStyle();
		XSSFFont fuente = this.libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);

		Cell celda = row.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);

		celda = row.createCell(1);
		celda.setCellValue("Usuario");
		celda.setCellStyle(estilo);

		celda = row.createCell(2);
		celda.setCellValue("Bloquedado");
		celda.setCellStyle(estilo);

		celda = row.createCell(3);
		celda.setCellValue("DesaBilitado");
		celda.setCellStyle(estilo);
	}

	private void writeCabTableReglas() {
		Row row = this.sheet.createRow(0);
		CellStyle estilo = this.libro.createCellStyle();
		XSSFFont fuente = this.libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);

		Cell celda = row.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);

		celda = row.createCell(1);
		celda.setCellValue("Desde");
		celda.setCellStyle(estilo);

		celda = row.createCell(2);
		celda.setCellValue("Asta");
		celda.setCellStyle(estilo);

		celda = row.createCell(3);
		celda.setCellValue("Dias");
		celda.setCellStyle(estilo);
	}

	private void writeDetalleTabla() {
		int nfil = 1;
		CellStyle estilo = this.libro.createCellStyle();
		XSSFFont fuente = this.libro.createFont();
		fuente.setFontHeight(14);
		CellStyle estiloF = this.libro.createCellStyle();
		XSSFFont fuenteF = this.libro.createFont();
		fuenteF.setFontHeight(14);
		estiloF.setFont(fuente);
		estiloF.setDataFormat((short) 14);
		for (Empleados empleado : this.lempleados) {
			Row row = this.sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(empleado.getId());
			this.sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(empleado.getNombre());
			this.sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(empleado.getApellidop());
			this.sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(empleado.getApellidom());
			this.sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);

			celda = row.createCell(4);
			celda.setCellValue("");//Edad
			this.sheet.autoSizeColumn(4);
			celda.setCellStyle(estilo);

			celda = row.createCell(5);
			celda.setCellValue(empleado.getSexo());
			this.sheet.autoSizeColumn(5);
			celda.setCellStyle(estilo);

			celda = row.createCell(6);
			celda.setCellValue(empleado.getCorreo());
			this.sheet.autoSizeColumn(6);
			celda.setCellStyle(estilo);

			celda = row.createCell(7);
			celda.setCellValue(empleado.getTelefono());
			this.sheet.autoSizeColumn(7);
			celda.setCellStyle(estilo);

			celda = row.createCell(9);
			celda.setCellValue(empleado.getFechaIngreso());
			this.sheet.autoSizeColumn(9);
			celda.setCellStyle(estiloF);

			celda = row.createCell(8);
//			celda.setCellValue(empleado.getSueldoNeto().toString());
			this.sheet.autoSizeColumn(8);
			celda.setCellStyle(estilo);
			
			LocalDate hoy = LocalDate.now();
			LocalDate fechaIngreso = new java.sql.Date(empleado.getFechaIngreso().getTime()).toLocalDate();
			Period periodo = Period.between(fechaIngreso, hoy);
			celda = row.createCell(10);
			celda.setCellValue(periodo.getYears());
			this.sheet.autoSizeColumn(10);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(11);
			celda.setCellValue(periodo.getMonths());
			this.sheet.autoSizeColumn(11);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(12);
			celda.setCellValue(periodo.getDays());
			this.sheet.autoSizeColumn(12);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(13);
			celda.setCellValue(empleado.getEmpleadoV().getDiasVacaciones());
			this.sheet.autoSizeColumn(13);
			celda.setCellStyle(estilo);
		}
	}

	private void writeDetalleTablaUsuarios() {
		int nfil = 1;
		CellStyle estilo = this.libro.createCellStyle();
		XSSFFont fuente = this.libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		for (UsuariosEntity usu : this.lusuarios) {
			Row row = this.sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(usu.getId());
			this.sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(usu.getUsername());
			this.sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(usu.getBloqueado());
			this.sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(usu.getDisabled());
			this.sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);
		}
	}

	private void writeDetalleReglas() {
		int nfil = 1;
		CellStyle estilo = this.libro.createCellStyle();
		XSSFFont fuente = this.libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		for (ReglasDiasEntity regla : this.lreglas) {
			Row row = this.sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(regla.getId());
			this.sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(regla.getDesde());
			this.sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(regla.getAsta());
			this.sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(regla.getDias());
			this.sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);
		}
	}

}
