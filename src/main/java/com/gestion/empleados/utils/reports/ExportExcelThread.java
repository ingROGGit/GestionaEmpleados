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

import com.gestion.empleados.entity.DetalleDeduccionesEntiy;
import com.gestion.empleados.entity.DetallePersepcionesEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.repository.ReglasDiasRepository;

public class ExportExcelThread extends Thread  {
	private String tipo;
	private XSSFWorkbook libro;
	private XSSFSheet sheet;
	private List<Empleados> lempleados;
	private List<UsuariosEntity> lusuarios;
	private List<ReglasDiasEntity> lreglas;
	private String nameFile;
	private String quinCatSelectGet;
	public ExportExcelThread(String nameFile ,String tipo,List<UsuariosEntity> lusuarios,List<ReglasDiasRepository> lReglas,List<Empleados> lempleados,String quinCatSelectGet) {
		this.libro = new XSSFWorkbook();
		this.lusuarios = lusuarios;
		this.lreglas = lreglas;
		this.lempleados = lempleados;
		this.nameFile=nameFile;
		this.tipo=tipo;
		this.quinCatSelectGet=quinCatSelectGet;
	}
	@Override
	public void run() {
		try {
			if (this.tipo == "Empleados") {
				this.sheet = this.libro.createSheet("Empleados");
				writeCabTable();
				writeDetalleTabla();
				
			}
//			if (this.tipo == "Reglas") {
//				this.sheet = this.libro.createSheet("Reglas");
//				writeCabTableReglas();
//				writeDetalleReglas();
//			}
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
		celda.setCellValue("CURP");
		celda.setCellStyle(estilo);

		celda = row.createCell(5);
		celda.setCellValue("RFC");
		celda.setCellStyle(estilo);

		celda = row.createCell(6);
		celda.setCellValue("ClaveP");
		celda.setCellStyle(estilo);

		celda = row.createCell(7);
		celda.setCellValue("PLAZA");
		celda.setCellStyle(estilo);

		celda = row.createCell(8);
		celda.setCellValue("Sueldo Pre");
		celda.setCellStyle(estilo);
		
		celda = row.createCell(9);
		celda.setCellValue("Sueldo Neto");
		celda.setCellStyle(estilo);	
		
		celda = row.createCell(10);
		celda.setCellValue("Tipo Pago");
		celda.setCellStyle(estilo);
		
		celda = row.createCell(11);
		celda.setCellValue("Folio Pre");
		celda.setCellStyle(estilo);
		
		celda = row.createCell(12);
		celda.setCellValue("Folio Quincena");
		celda.setCellStyle(estilo);	
		
		celda = row.createCell(13);
		celda.setCellValue("Fecha Ingreso");
		celda.setCellStyle(estilo);

		celda = row.createCell(14);
		celda.setCellValue("Telefono");
		celda.setCellStyle(estilo);
		celda = row.createCell(15);
		celda.setCellValue("Puesto");
		celda.setCellStyle(estilo);
		celda = row.createCell(16);
		celda.setCellValue("Servicio");
		celda.setCellStyle(estilo);
		celda = row.createCell(17);
		celda.setCellValue("P 04");
		celda.setCellStyle(estilo);
		celda = row.createCell(18);
		celda.setCellValue("D 50");
		celda.setCellStyle(estilo);
		celda = row.createCell(19);
		celda.setCellValue("D D2");
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
			celda.setCellValue(empleado.getCurp());
			this.sheet.autoSizeColumn(4);
			celda.setCellStyle(estilo);

			celda = row.createCell(5);
			celda.setCellValue(empleado.getRfc());
			this.sheet.autoSizeColumn(5);
			celda.setCellStyle(estilo);

			celda = row.createCell(6);
			celda.setCellValue(empleado.getClaveP());
			this.sheet.autoSizeColumn(6);
			celda.setCellStyle(estilo);

			celda = row.createCell(7);
			celda.setCellValue(empleado.getPlaza());
			this.sheet.autoSizeColumn(7);
			celda.setCellStyle(estilo);

			celda = row.createCell(8);
			celda.setCellValue(empleado.getQuincenas().get(0).getSueldoPre()!=null?empleado.getQuincenas().get(0).getSueldoPre().toString():"");
			this.sheet.autoSizeColumn(8);
			celda.setCellStyle(estiloF);
			
			celda = row.createCell(9);
			celda.setCellValue(empleado.getQuincenas().get(0).getSueldoNeto()!=null?empleado.getQuincenas().get(0).getSueldoNeto().toString():"");
			this.sheet.autoSizeColumn(9);
			celda.setCellStyle(estiloF);

			celda = row.createCell(10);
			celda.setCellValue(empleado.getQuincenas().get(0).getTipoPago()!=null?empleado.getQuincenas().get(0).getTipoPago().toString():"");
			this.sheet.autoSizeColumn(10);
			celda.setCellStyle(estiloF);
			
			celda = row.createCell(11);
			celda.setCellValue(empleado.getQuincenas().get(0).getFolioPre()!=null?empleado.getQuincenas().get(0).getFolioPre().toString():"");
			this.sheet.autoSizeColumn(11);
			celda.setCellStyle(estiloF);
			
			celda = row.createCell(12);
			celda.setCellValue(empleado.getQuincenas().get(0).getFolioQuin()!=null?empleado.getQuincenas().get(0).getFolioQuin().toString():"");
			this.sheet.autoSizeColumn(12);
			celda.setCellStyle(estiloF);
			
			celda = row.createCell(13);
			celda.setCellValue(empleado.getFechaIngreso());
			this.sheet.autoSizeColumn(13);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(14);
			celda.setCellValue(empleado.getTelefono());
			this.sheet.autoSizeColumn(14);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(15);
			celda.setCellValue(empleado.getPuestosEntity()!=null?empleado.getPuestosEntity().getPuesto():"");
			this.sheet.autoSizeColumn(15);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(16);
			celda.setCellValue(empleado.getServicioEntity()!=null?empleado.getServicioEntity().getServicio():"");
			this.sheet.autoSizeColumn(16);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(17);
			DetallePersepcionesEntity priesgo = empleado.getDetallePercepcione().stream()
				    .filter(detalle -> "04".equals(detalle.getPersepciones().getClave()))
				    .findFirst()
				    .orElse(null);
			celda.setCellValue(priesgo!=null?priesgo.getImporte().toString():"0.00");
			this.sheet.autoSizeColumn(17);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(18);
			DetalleDeduccionesEntiy dIncapacidad = empleado.getDetalleDeducciones().stream()
				    .filter(detalle -> "50".equals(detalle.getDeducciones().getClave()))
				    .findFirst()
				    .orElse(null);
			celda.setCellValue(dIncapacidad!=null?dIncapacidad.getImporte().toString():"0.00");
			this.sheet.autoSizeColumn(18);
			celda.setCellStyle(estilo);
			
			celda = row.createCell(19);
			DetalleDeduccionesEntiy dD2 = empleado.getDetalleDeducciones().stream()
				    .filter(detalle -> "D2".equals(detalle.getDeducciones().getClave()))
				    .findFirst()
				    .orElse(null);
			celda.setCellValue(dD2!=null?dD2.getImporte().toString():"0.00");
			this.sheet.autoSizeColumn(19);
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
