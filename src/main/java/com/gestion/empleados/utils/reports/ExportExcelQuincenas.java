package com.gestion.empleados.utils.reports;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.gestion.empleados.entity.DetalleDeduccionesEntiy;
import com.gestion.empleados.entity.DetallePersepcionesEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.QuincenasEntity;
import com.gestion.empleados.entity.ReglasDiasEntity;
import com.gestion.empleados.entity.UsuariosEntity;
import com.gestion.empleados.entity.filtrosConsultaDTO;
import com.gestion.empleados.repository.DetalleDeduccionesRepositoryJPA;
import com.gestion.empleados.repository.DetallePersepcionesRepositoryJPA;
import com.gestion.empleados.repository.ReglasDiasRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class ExportExcelQuincenas {
	private XSSFWorkbook libro;
	private XSSFSheet sheet;
	private List<QuincenasEntity> lquincenas;
	private String nameFile;
	private filtrosConsultaDTO filtrosSet;
	private DetallePersepcionesRepositoryJPA detallePerJPA;
	private DetalleDeduccionesRepositoryJPA detalleDedJPA;
	public ExportExcelQuincenas(filtrosConsultaDTO filtrosSet,List<QuincenasEntity> lquincenas, String idQNA, String nameFile) {
		libro = new XSSFWorkbook();
		sheet = libro.createSheet(idQNA);
		this.lquincenas = lquincenas;
		this.nameFile = nameFile;
		this.filtrosSet=filtrosSet;
	}

	private void writeCabTable(String tipoFiltro) {
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
		if(tipoFiltro.equals("TipoContrato")){
			celda = row.createCell(15);
			celda.setCellValue("TipoContrato");
			celda.setCellStyle(estilo);
		}
		if(tipoFiltro.equals("Quinquenio")){
			celda = row.createCell(15);
			celda.setCellValue("Quinquenio");
			celda.setCellStyle(estilo);
		}
		if (tipoFiltro.equals("Puesto")) {
			celda = row.createCell(15);
			celda.setCellValue("Puesto");
			celda.setCellStyle(estilo);
		}
		if (tipoFiltro.equals("Servicio")) {
			celda = row.createCell(15);
			celda.setCellValue("Servicio");
			celda.setCellStyle(estilo);
		}
		if (tipoFiltro.equals("TipoPago")) {
			celda = row.createCell(15);
			celda.setCellValue("TipoPago");
			celda.setCellStyle(estilo);
		}
		if (tipoFiltro.equals("Bloqueados")) {
			celda = row.createCell(15);
			celda.setCellValue("Bloqueados");
			celda.setCellStyle(estilo);
		}
		if (tipoFiltro.equals("Persepciones")) {
			celda = row.createCell(15);
			celda.setCellValue("Persepciones");
			celda.setCellStyle(estilo);
			row = sheet.createRow(1);
			estilo = libro.createCellStyle();
			fuente = libro.createFont();
			fuente.setBold(true);
			fuente.setFontHeight(16);
			estilo.setFont(fuente);
			celda = row.createCell(15);
			celda.setCellValue("Clave");
			celda.setCellStyle(estilo);
			celda = row.createCell(16);
			celda.setCellValue("Descripsion");
			celda.setCellStyle(estilo);
			celda = row.createCell(17);
			celda.setCellValue("Importe");
			celda.setCellStyle(estilo);
		}
		if (tipoFiltro.equals("Deducciones")) {
			celda = row.createCell(15);
			celda.setCellValue("Deducciones");
			celda.setCellStyle(estilo);
			row = sheet.createRow(1);
			estilo = libro.createCellStyle();
			fuente = libro.createFont();
			fuente.setBold(true);
			fuente.setFontHeight(16);
			estilo.setFont(fuente);
			celda = row.createCell(15);
			celda.setCellValue("Clave");
			celda.setCellStyle(estilo);
			celda = row.createCell(16);
			celda.setCellValue("Descripsion");
			celda.setCellStyle(estilo);
			celda = row.createCell(17);
			celda.setCellValue("Importe");
			celda.setCellStyle(estilo);
		}
	}

	private void writeDetalleTabla(String tipoFiltro) {
		int nfil = 2;
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		fuente.setFontHeight(14);
		CellStyle estiloF = this.libro.createCellStyle();
		XSSFFont fuenteF = this.libro.createFont();
		fuenteF.setFontHeight(14);
		estiloF.setFont(fuente);
		estiloF.setDataFormat((short) 14);
		for (QuincenasEntity quincena : this.lquincenas) {
			Row row = sheet.createRow(nfil++);
			Cell celda = row.createCell(0);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getId());
			sheet.autoSizeColumn(0);
			celda.setCellStyle(estilo);

			celda = row.createCell(1);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getNombre());
			this.sheet.autoSizeColumn(1);
			celda.setCellStyle(estilo);

			celda = row.createCell(2);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getApellidop());
			this.sheet.autoSizeColumn(2);
			celda.setCellStyle(estilo);

			celda = row.createCell(3);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getApellidom());
			this.sheet.autoSizeColumn(3);
			celda.setCellStyle(estilo);

			celda = row.createCell(4);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getCurp());
			this.sheet.autoSizeColumn(4);
			celda.setCellStyle(estilo);

			celda = row.createCell(5);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getRfc());
			this.sheet.autoSizeColumn(5);
			celda.setCellStyle(estilo);

			celda = row.createCell(6);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getClaveP());
			this.sheet.autoSizeColumn(6);
			celda.setCellStyle(estilo);

			celda = row.createCell(7);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getPlaza());
			this.sheet.autoSizeColumn(7);
			celda.setCellStyle(estilo);

			celda = row.createCell(8);
			celda.setCellValue(quincena.getSueldoPre() != null ? quincena.getSueldoPre().toString() : "");
			this.sheet.autoSizeColumn(8);
			celda.setCellStyle(estiloF);

			celda = row.createCell(9);
			celda.setCellValue(quincena.getSueldoNeto() != null ? quincena.getSueldoNeto().toString() : "");
			this.sheet.autoSizeColumn(9);
			celda.setCellStyle(estiloF);

			celda = row.createCell(10);
			celda.setCellValue(quincena.getTipoPago() != null ? quincena.getTipoPago().toString() : "");
			this.sheet.autoSizeColumn(10);
			celda.setCellStyle(estiloF);

			celda = row.createCell(11);
			celda.setCellValue(quincena.getFolioPre() != null ? quincena.getFolioPre().toString() : "");
			this.sheet.autoSizeColumn(11);
			celda.setCellStyle(estiloF);

			celda = row.createCell(12);
			celda.setCellValue(quincena.getFolioQuin() != null ? quincena.getFolioQuin().toString() : "");
			this.sheet.autoSizeColumn(12);
			celda.setCellStyle(estiloF);

			celda = row.createCell(13);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getFechaIngreso());
			this.sheet.autoSizeColumn(13);
			celda.setCellStyle(estilo);

			celda = row.createCell(14);
			celda.setCellValue(quincena.getEmpleadoQN().get(0).getTelefono());
			this.sheet.autoSizeColumn(14);
			celda.setCellStyle(estilo);
			if(tipoFiltro.equals("TipoContrato")){
				celda = row.createCell(15);
				celda.setCellValue(quincena.getEmpleadoQN().get(0).getTipoContrato() != null
						? quincena.getEmpleadoQN().get(0).getTipoContrato()
						: "");
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
			}
			if(tipoFiltro.equals("Quinquenio")){
				celda = row.createCell(15);
				celda.setCellValue(quincena.isQuinquenio()?"APLICA":"NO APLICA");
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
				DetallePersepcionesEntity detallePer= detallePerJPA.findByPersepciones_IdAndEmpleadoPer_IdAndQuincenaCatDP_IdQNA(Long.valueOf(6),quincena.getEmpleadoQN().get(0).getId(),quincena.getQuinCat().iterator().next().getIdQNA());
				celda = row.createCell(16);
				celda.setCellValue(detallePer.getPersepciones().getClave());
				this.sheet.autoSizeColumn(16);
				celda.setCellStyle(estilo);
				celda = row.createCell(17);
				celda.setCellValue(detallePer.getPersepciones().getDescripcion());
				this.sheet.autoSizeColumn(17);
				celda.setCellStyle(estilo);
				celda = row.createCell(18);
				celda.setCellValue(detallePer.getImporte().toString());
				this.sheet.autoSizeColumn(18);
				celda.setCellStyle(estilo);
			}
			if (tipoFiltro.equals("Puesto")) {
				celda = row.createCell(15);
				celda.setCellValue(quincena.getEmpleadoQN().get(0).getPuestosEntity() != null
						? quincena.getEmpleadoQN().get(0).getPuestosEntity().getPuesto()
						: "");
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
			}
			if (tipoFiltro.equals("Servicio")) {
				celda = row.createCell(15);
				celda.setCellValue(quincena.getEmpleadoQN().get(0).getServicioEntity() != null
						? quincena.getEmpleadoQN().get(0).getServicioEntity().getServicio()
						: "");
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
			}
			if (tipoFiltro.equals("TipoPago")) {
				celda = row.createCell(15);
				celda.setCellValue(quincena.getTipoPago());
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
			}
			if (tipoFiltro.equals("Bloqueados")) {
				celda = row.createCell(15);
				celda.setCellValue(quincena.isBloqueoPago()?"PAGO BLOQUEADO":"PAGO ACTIVO");
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
			}
			if (tipoFiltro.equals("Persepciones")) {
				DetallePersepcionesEntity detallePer= detallePerJPA.findByPersepciones_IdAndEmpleadoPer_IdAndQuincenaCatDP_IdQNA(this.filtrosSet.getIdPersepcion(),quincena.getEmpleadoQN().get(0).getId(),quincena.getQuinCat().iterator().next().getIdQNA());
				celda = row.createCell(15);
				celda.setCellValue(detallePer.getPersepciones().getClave());
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
				celda = row.createCell(16);
				celda.setCellValue(detallePer.getPersepciones().getDescripcion());
				this.sheet.autoSizeColumn(16);
				celda.setCellStyle(estilo);
				celda = row.createCell(17);
				celda.setCellValue(detallePer.getImporte().toString());
				this.sheet.autoSizeColumn(17);
				celda.setCellStyle(estilo);
			}
			if (tipoFiltro.equals("Deducciones")) {
				DetalleDeduccionesEntiy detallePer= detalleDedJPA.findByDeducciones_IdAndEmpleadoDD_IdAndQuincenaCatDD_IdQNA(this.filtrosSet.getIdDeduccion(),quincena.getEmpleadoQN().get(0).getId(),quincena.getQuinCat().iterator().next().getIdQNA());
				celda = row.createCell(15);
				celda.setCellValue(detallePer.getDeducciones().getClave());
				this.sheet.autoSizeColumn(15);
				celda.setCellStyle(estilo);
				celda = row.createCell(16);
				celda.setCellValue(detallePer.getDeducciones().getDescripcion());
				this.sheet.autoSizeColumn(16);
				celda.setCellStyle(estilo);
				celda = row.createCell(17);
				celda.setCellValue(detallePer.getImporte().toString());
				this.sheet.autoSizeColumn(17);
				celda.setCellStyle(estilo);
			}
		}
	}

	public void exportarExcel(String tipoFiltro,DetallePersepcionesRepositoryJPA detallePerJPA,DetalleDeduccionesRepositoryJPA detalleDedJPA) throws IOException {
		this.detallePerJPA=detallePerJPA;
		this.detalleDedJPA=detalleDedJPA;
		writeCabTable(tipoFiltro);
		writeDetalleTabla(tipoFiltro);
		// Escribir el archivo final
		FileOutputStream fileOut = new FileOutputStream(this.nameFile);
		libro.write(fileOut);
		System.out.println("¡Plantilla Excel generada con éxito!");
		libro.close();
	}
}
