package com.gestion.empleados.utils.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.gestion.empleados.JPA.Empleados;
import com.gestion.empleados.entity.Empleado;
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

public class ExporterPDF {

	private List<Empleados> listaEmpleados;

	public ExporterPDF(List<Empleados> listaEmpleados) {
		super();
		this.listaEmpleados = listaEmpleados;
	}
	private void writeCabeceraTabla(PdfPTable pdftable) {
		PdfPCell celda= new PdfPCell();
		celda.setBackgroundColor(Color.RED);
		celda.setPadding(5);
		Font fuente= FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		celda.setPhrase(new Phrase("ID",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Nombre",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Apellido P",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Apellido M",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Edad",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Sexo",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Salario",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Correo",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Telefono",fuente));
		pdftable.addCell(celda);
		celda.setPhrase(new Phrase("Fecha",fuente));
		pdftable.addCell(celda);
	}
	private void writeDetalleTabla(PdfPTable pdftable) {
		for(Empleados empleado: listaEmpleados) {
			pdftable.addCell(String.valueOf(empleado.getId()));
			pdftable.addCell(empleado.getNombre());
			pdftable.addCell(empleado.getApellidop());
			pdftable.addCell(empleado.getApellidom());
			pdftable.addCell(String.valueOf(empleado.getEdad()));
			pdftable.addCell(empleado.getSexo());
			pdftable.addCell(String.valueOf(empleado.getSalario()));
			pdftable.addCell(empleado.getCorreo());
			pdftable.addCell(String.valueOf(empleado.getTelefono()));
			pdftable.addCell(empleado.getFecha().toString());
		}
	}
	public void exportarPDF(HttpServletResponse response) throws DocumentException, IOException {
		Document documento=new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		Font fuente= FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.BLUE);
		fuente.setSize(15);
		Paragraph titulo= new Paragraph("Lista de Empleados",fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		PdfPTable table = new PdfPTable(10);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		table.setWidths(new float[] {1f,2.3f,2.3f,2.3f,2f,2.3f,2.3f,3.5f,2.2f,2.2f});
		table.setWidthPercentage(110);
		writeCabeceraTabla(table);
		writeDetalleTabla(table);
		documento.add(table);
		documento.close();
	}
}
