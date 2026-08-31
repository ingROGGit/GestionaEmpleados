package com.gestion.empleados.utils.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.gestion.empleados.entity.DeduccionesEntity;
import com.gestion.empleados.entity.PersepcionesEntity;
import com.gestion.empleados.entity.PuestosEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.ServiciosEntity;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

public class ExporterPDFCatalogos {

	private List<PuestosEntity> listPuestos;
	private List<ServiciosEntity> listServicios;
	private List<DeduccionesEntity> listDeducciones;
	private List<PersepcionesEntity> listPersepciones;
	private List<QuincenaCatEntity> listQuincenaCat;
	public ExporterPDFCatalogos(List<?> lista,String TIPO) {
		super();
		if(TIPO.equals("CPU")) {
			this.listPuestos = (List<PuestosEntity>) lista;
		}
		if(TIPO.equals("CSE")) {
			this.listServicios = (List<ServiciosEntity>) lista;
		}
		if(TIPO.equals("CDED")) {
			this.listDeducciones = (List<DeduccionesEntity>) lista;
		}
		if(TIPO.equals("CPER")) {
			this.listPersepciones = (List<PersepcionesEntity>) lista;
		}
		if(TIPO.equals("CQNA")) {
			listQuincenaCat=(List<QuincenaCatEntity>) lista;
		}
	}
	private void writeCabeceraTabla(PdfPTable pdftable,String TIPO) {
		PdfPCell celda= new PdfPCell();
		celda.setBackgroundColor(Color.BLACK);
		celda.setPadding(5);
		Font fuente= FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		if(TIPO.equals("CQNA")) {
			celda.setPhrase(new Phrase("ID",fuente));
			pdftable.addCell(celda);
			celda.setPhrase(new Phrase("FECHA INICIO",fuente));
			pdftable.addCell(celda);
			celda.setPhrase(new Phrase("FECHA FIN",fuente));
			pdftable.addCell(celda);
			celda.setPhrase(new Phrase("FECHA DE PAGO",fuente));
			pdftable.addCell(celda);
			celda.setPhrase(new Phrase("FECHA DE CHEQUE",fuente));
			pdftable.addCell(celda);
			celda.setPhrase(new Phrase("FECHA SUSPENCION",fuente));
			pdftable.addCell(celda);
		}
		else {
		celda.setPhrase(new Phrase("CLAVE",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("DESCRIPCION",fuente));
		pdftable.addCell(celda);
		}
	}
	private void writeDetalleTabla(PdfPTable pdftable,String TIPO) {
		if(TIPO.equals("CPU")) {
			for(PuestosEntity objeto: this.listPuestos) {
				pdftable.addCell(String.valueOf(objeto.getId()));
				pdftable.addCell(objeto.getPuesto());
			}
		}
		if(TIPO.equals("CSE")) {
			for(ServiciosEntity objeto: this.listServicios) {
				pdftable.addCell(String.valueOf(objeto.getId()));
				pdftable.addCell(objeto.getServicio());
			}
		}
		if(TIPO.equals("CDED")) {
			for(DeduccionesEntity objeto: this.listDeducciones) {
				pdftable.addCell((objeto.getClave()));
				pdftable.addCell(objeto.getDescripcion());
			}
		}
		if(TIPO.equals("CPER")) {
			for(PersepcionesEntity objeto: this.listPersepciones) {
				pdftable.addCell((objeto.getClave()));
				pdftable.addCell(objeto.getDescripcion());
			}
		}
		if(TIPO.equals("CQNA")) {
			for(QuincenaCatEntity objeto: this.listQuincenaCat) {
				pdftable.addCell((objeto.getIdQNA()));
				pdftable.addCell(objeto.getFechaInicio().toString());
				pdftable.addCell(objeto.getFechaFin().toString());
				pdftable.addCell(objeto.getFechaPago().toString());
				pdftable.addCell(objeto.getFechaCheque().toString());
				pdftable.addCell(objeto.getFechaSuspencion().toString());
			}
		}
	}
	public void exportarPDF(HttpServletResponse response,String TIPO,String tipoValor) throws DocumentException, IOException {
		Document documento=new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		Font fuente= FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.BLUE);			
		fuente.setSize(15);
		Paragraph titulo= new Paragraph("LISTADO DE "+tipoValor,fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		PdfPTable table;
		if(TIPO.equals("CQNA"))
			table = new PdfPTable(6);
		else
			table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		if(TIPO.equals("CQNA"))
			table.setWidths(new float[] {3.5f,3.5f,3.5f,3.5f,3.5f,3.5f});
		else
			table.setWidths(new float[] {3.5f,10f});
		table.setWidthPercentage(110);
		writeCabeceraTabla(table,TIPO);
		writeDetalleTabla(table,TIPO);
		documento.add(table);
		documento.close();
	}
}
