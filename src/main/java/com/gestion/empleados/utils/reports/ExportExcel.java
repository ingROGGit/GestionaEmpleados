package com.gestion.empleados.utils.reports;

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

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExportExcel {
	private XSSFWorkbook libro;
	private XSSFSheet sheet;
	private List<Empleados> lempleados;
	private List<UsuariosEntity> lusuarios;
	private List<ReglasDiasEntity> lreglas;

	public void ExportExcelUsuarios(List<UsuariosEntity> lusuarios) {
		libro = new XSSFWorkbook();
		sheet = libro.createSheet("Usuarios");
		this.lusuarios = lusuarios;
	}

	public void ExportExcelReglas(List<ReglasDiasEntity> lReglas) {
		libro = new XSSFWorkbook();
		sheet = libro.createSheet("Reglas");
		this.lreglas = lreglas;
	}

	public void ExportExcel(List<Empleados> lempleados) {
		libro = new XSSFWorkbook();
		sheet = libro.createSheet("Empleados");
		this.lempleados = lempleados;
	}

	private void writeCabTable() {
		Row row = sheet.createRow(0);
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
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
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);

		celda = row.createCell(10);
		celda.setCellValue("Años lavorados");
		celda.setCellStyle(estilo);
		celda = row.createCell(11);
		celda.setCellValue("Meses lavorados");
		celda.setCellStyle(estilo);
		celda = row.createCell(12);
		celda.setCellValue("Dias lavorados");
		celda.setCellStyle(estilo);
	}

	private void writeCabTableUsuarios() {
		Row row = sheet.createRow(0);
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
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
		Row row = sheet.createRow(0);
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
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
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		for (Empleados empleado : lempleados) {
			Row row = sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(empleado.getId());
			sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(empleado.getNombre());
			sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(empleado.getApellidop());
			sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(empleado.getApellidom());
			sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);

			celda = row.createCell(4);
			celda.setCellValue("");//Edad
			sheet.autoSizeColumn(4);
			celda.setCellStyle(estilo);

			celda = row.createCell(5);
			celda.setCellValue(empleado.getSexo());
			sheet.autoSizeColumn(5);
			celda.setCellStyle(estilo);

			celda = row.createCell(6);
			celda.setCellValue(empleado.getCorreo());
			sheet.autoSizeColumn(6);
			celda.setCellStyle(estilo);

			celda = row.createCell(7);
			celda.setCellValue(empleado.getTelefono());
			sheet.autoSizeColumn(7);
			celda.setCellStyle(estilo);

			celda = row.createCell(9);
			celda.setCellValue(empleado.getFechaIngreso());
			sheet.autoSizeColumn(9);
			celda.setCellStyle(estilo);

			celda = row.createCell(8);
//			celda.setCellValue(empleado.getSueldoNeto().toString());
			celda.setCellValue("");
			sheet.autoSizeColumn(8);
			celda.setCellStyle(estilo);
			
			LocalDate hoy = LocalDate.now();
			LocalDate fechaIngreso = new java.sql.Date(empleado.getFechaIngreso().getTime()).toLocalDate();
			Period periodo = Period.between(fechaIngreso, hoy);
			celda = row.createCell(10);
			celda.setCellValue(periodo.getYears());
			sheet.autoSizeColumn(10);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(11);
			celda.setCellValue(periodo.getMonths());
			sheet.autoSizeColumn(11);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(12);
			celda.setCellValue(periodo.getDays());
			sheet.autoSizeColumn(12);
			celda.setCellStyle(estilo);
		}
	}

	private void writeDetalleTablaUsuarios() {
		int nfil = 1;
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		for (UsuariosEntity usu : lusuarios) {
			Row row = sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(usu.getId());
			sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(usu.getUsername());
			sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(usu.getBloqueado());
			sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(usu.getDisabled());
			sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);
		}
	}

	private void writeDetalleReglas() {
		int nfil = 1;
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		for (ReglasDiasEntity regla : lreglas) {
			Row row = sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(regla.getId());
			sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(regla.getDesde());
			sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(regla.getAsta());
			sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(regla.getDias());
			sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);
		}
	}

	public void exportarExcel(HttpServletResponse response, String tipo) throws IOException {
		if (tipo == "Empleados") {
			writeCabTable();
			writeDetalleTabla();
		}
		if (tipo == "Reglas") {
			writeCabTableReglas();
			writeDetalleReglas();
		}
		if (tipo == "Usuarios") {
			writeCabTableUsuarios();
			writeDetalleTablaUsuarios();
		}
		ServletOutputStream outputS = response.getOutputStream();
		libro.write(outputS);
		libro.close();
		outputS.close();
	}
}
