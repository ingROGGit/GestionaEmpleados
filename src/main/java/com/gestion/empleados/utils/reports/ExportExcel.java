package com.gestion.empleados.utils.reports;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestion.empleados.JPA.Empleados;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExportExcel {
	private XSSFWorkbook libro;
	private XSSFSheet sheet;
	private List<Empleados> lempleados;

	public ExportExcel(List<Empleados> lempleados) {
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
		celda.setCellValue("ID");
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
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);

		celda = row.createCell(9);
		celda.setCellValue("Salario");
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
			celda.setCellValue(empleado.getEdad());
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

			celda = row.createCell(8);
			celda.setCellValue(empleado.getFecha());
			sheet.autoSizeColumn(8);
			celda.setCellStyle(estilo);

			celda = row.createCell(9);
			celda.setCellValue(empleado.getSalario().toString());
			sheet.autoSizeColumn(9);
			celda.setCellStyle(estilo);
		}
	}

	public void exportarExcel(HttpServletResponse response) throws IOException {
		writeCabTable();
		writeDetalleTabla();
		ServletOutputStream outputS = response.getOutputStream();
		libro.write(outputS);
		libro.close();
		outputS.close();
	}
}
