package com.gestion.empleados.utils.reports;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestion.empleados.entity.CuentasEntity;
import com.gestion.empleados.entity.Empleados;
import com.gestion.empleados.entity.JefesEntity;
import com.gestion.empleados.entity.QuincenaCatEntity;
import com.gestion.empleados.entity.TESONESEntity;
import com.gestion.empleados.repository.TESONRepository;

public class ExportTESONES{
	private XSSFWorkbook libro;
	private String nameFile;
	private QuincenaCatEntity quincenaCat;
	private List<TESONESEntity> ltesones;
	private String tipoNom;
	private JefesEntity jefe;
	private List<Long> lfolisCheques;
	public ExportTESONES(String nameFile ,List<TESONESEntity> ltesones,QuincenaCatEntity quincenaCat,String tipoNom,JefesEntity jefe,List<Long> lfolisCheques) {
		this.libro = new XSSFWorkbook();
		this.ltesones=ltesones;
		this.nameFile=nameFile;
		this.quincenaCat=quincenaCat;
		this.tipoNom=tipoNom;
		this.jefe=jefe;
		this.lfolisCheques=lfolisCheques;
	}
	public void run() throws Exception{
		try {
				Long DEL,AL;
				DEL=Collections.min(this.lfolisCheques);
				AL=Collections.max(this.lfolisCheques);
				LocalDate hoy=LocalDate.now();
				// Define el formato "MMMM" (nombre completo del mes) en español
		        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));

				Workbook workbook = new XSSFWorkbook();
	            Sheet sheet = workbook.createSheet("TESON");

	         // --- ESTILOS DE CELDAS ---
	         // 1. Crear una nueva fuente y definir el tamaño (ej. 14 puntos)
	            Font fuente = workbook.createFont();
	            fuente.setFontHeightInPoints((short) 10);
	            
	         // --- 1. DEFINICIÓN DE ESTILOS ---
	            // Estilo de Texto Negrita Centrado
	            CellStyle boldCenterStyle = workbook.createCellStyle();
	            Font boldFont = workbook.createFont();
	            boldFont.setBold(true);
	            boldCenterStyle.setFont(boldFont);
	            boldCenterStyle.setAlignment(HorizontalAlignment.CENTER);

	            // Estilo para los encabezados de la tabla principal
	            CellStyle headerStyle = workbook.createCellStyle();
	            headerStyle.setFont(boldFont);
	            headerStyle.setAlignment(HorizontalAlignment.LEFT);
	            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	            headerStyle.setBorderBottom(BorderStyle.THICK);
	            headerStyle.setBorderTop(BorderStyle.THICK);
	            headerStyle.setBorderLeft(BorderStyle.THICK);
	            headerStyle.setBorderRight(BorderStyle.THICK);
	            
	         // Aliniacion Centrada
	            CellStyle headerStyleCenter = workbook.createCellStyle();
	            headerStyleCenter.setFont(boldFont);
	            headerStyleCenter.setAlignment(HorizontalAlignment.CENTER);
	            headerStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
	            headerStyleCenter.setBorderBottom(BorderStyle.THICK);
	            headerStyleCenter.setBorderTop(BorderStyle.THICK);
	            headerStyleCenter.setBorderLeft(BorderStyle.THICK);
	            headerStyleCenter.setBorderRight(BorderStyle.THICK);
	            
	         // Estilo Detalle de tabla
	            CellStyle detalleTabla = workbook.createCellStyle();
	            detalleTabla.setFont(boldFont);
	            detalleTabla.setAlignment(HorizontalAlignment.CENTER);
	            detalleTabla.setVerticalAlignment(VerticalAlignment.CENTER);
	            detalleTabla.setBorderBottom(BorderStyle.THIN);
	            detalleTabla.setBorderTop(BorderStyle.THIN);
	            detalleTabla.setBorderLeft(BorderStyle.THICK);
	            detalleTabla.setBorderRight(BorderStyle.THICK);
	            
	            Cell celborder;
	            Cell celFirma;
	            CellRangeAddress region;
	            	            
	            
	            // Estilo básico para texto en negrita
	            CellStyle boldStyle = workbook.createCellStyle();
	            boldFont.setBold(true);
	            boldStyle.setFont(boldFont);

	            // Estilo para los encabezados de la tabla (Fondo gris opcional o solo negrita con bordes)
	            CellStyle headerTableStyle = workbook.createCellStyle();
	            headerTableStyle.setFont(boldFont);
	            headerTableStyle.setBorderBottom(BorderStyle.MEDIUM);
	            headerTableStyle.setBorderTop(BorderStyle.MEDIUM);
	            headerTableStyle.setAlignment(HorizontalAlignment.CENTER);

	            // Estilo centrado para firmas y fechas
	            CellStyle centerStyle = workbook.createCellStyle();
	            centerStyle.setAlignment(HorizontalAlignment.CENTER);
	            
	         // 2. Leer la imagen 1
	            InputStream is = getClass().getClassLoader().getResourceAsStream("ISSSTETESON.png");
	            byte[] bytes = IOUtils.toByteArray(is);
	            is.close();

	            // 3. Añadir la imagen al libro de trabajo y obtener el índice
	            // Puedes cambiar PICTURE_TYPE_PNG por PICTURE_TYPE_JPEG según tu archivo
	            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

	            // 4. Crear el objeto drawing (es el contenedor de las imágenes)
	            CreationHelper helper = workbook.getCreationHelper();
	            Drawing<?> drawing = sheet.createDrawingPatriarch();

	            // 5. Configurar la posición (Ancla) de la imagen
	            ClientAnchor anchor = helper.createClientAnchor();
	            
	            // Definir la celda de origen (Esquina superior izquierda)
	            anchor.setCol1(0); // Columna B (las columnas empiezan en 0)
	            anchor.setRow1(0); // Fila 2 (las filas empiezan en 0)

	            // 6. Crear la imagen en la posición indicada
	            Picture pict = drawing.createPicture(anchor, pictureIdx);

	            // 7. Auto-ajustar la imagen a su tamaño original
	            pict.resize(1.0);
	         
	            CellStyle style = workbook.createCellStyle();
	            style.setWrapText(true); 
	            style.setFont(fuente);
	            Row row1 = sheet.createRow(1);
	            region = new CellRangeAddress(1, 4, 1, 3);
	            sheet.addMergedRegion(region);
	            Cell cellIste = row1.createCell(1);
	            cellIste.setCellValue("Instituto de Seguridad \r\n"
	            		+ "y Servicios Sociales\r\n"
	            		+ "de los Trabajadores del Estado\r\n"
	            		+ "");
	            cellIste.setCellStyle(style);
	           // --- FILA 2 y 3: FECHA DE ELABORACIÓN ---
	            Row row2 = sheet.createRow(2);
	            region = new CellRangeAddress(2, 2, 4, 8);
	            sheet.addMergedRegion(region);
	            Cell cellFechaE = row2.createCell(4);
	            cellFechaE.setCellValue("FECHA DE ELABORACIÓN");
	            cellFechaE.setCellStyle(headerStyleCenter);
	            agregaMargenRegion(region,sheet);

	            Row  row3= sheet.createRow(3);
	            celborder=row3.createCell(4);
	            celborder.setCellValue("DIA:   "+hoy.getDayOfMonth());
	            celborder.setCellStyle(headerStyle);
	            
	            // Fila Mes Se concatena
	            region = new CellRangeAddress(3, 3, 5, 6);
	            sheet.addMergedRegion(region);
	            celborder=row3.createCell(5);
	            celborder.setCellValue(hoy.format(formateador).toUpperCase());
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	         // Fila del AÑO
	            region = new CellRangeAddress(3, 3, 7, 8);
	            sheet.addMergedRegion(region);
	            celborder=row3.createCell(7);
	            celborder.setCellValue("AÑO:   "+hoy.getYear());
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            // --- FILAS 5 a 8: METADATOS DE LA NÓMINA ---
	            Row row5 = sheet.createRow(5);
	            region = new CellRangeAddress(5, 5, 0, 2);
	            sheet.addMergedRegion(region);
	            celborder=row5.createCell(0);
	            celborder.setCellValue("NOMINA: "+this.tipoNom);
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            region = new CellRangeAddress(5, 5, 3, 8);
	            sheet.addMergedRegion(region);
	            celborder=row5.createCell(3);
	            celborder.setCellValue("FECHA DE EMISION "+this.quincenaCat.getFechaFin().toLocalDate().getDayOfMonth()+" DE "+this.quincenaCat.getFechaFin().toLocalDate().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
	                    .toUpperCase() +  " DEL "+ this.quincenaCat.getFechaFin().toLocalDate().getYear());
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            Row row6 = sheet.createRow(6);
	            region = new CellRangeAddress(6, 6, 0, 2);
	            sheet.addMergedRegion(region);
	            celborder=row6.createCell(0);
	            celborder.setCellValue("No. DE CHEQUE: "+DEL+" AL "+AL);
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            
	            region = new CellRangeAddress(6, 6, 3, 8);
	            sheet.addMergedRegion(region);
	            celborder=row6.createCell(3);
	            celborder.setCellValue("UNIDAD:            HOSPITAL REGIONAL B TLAJOMULCO");
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            
	            Row row7 = sheet.createRow(7);
	            region = new CellRangeAddress(7, 7, 0, 2);
	            sheet.addMergedRegion(region);
	            celborder=row7.createCell(0);
	            celborder.setCellValue("CLAVE DE ADSCRIPCIÓN:  4959");
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            region = new CellRangeAddress(7, 7, 3, 8);
	            sheet.addMergedRegion(region);
	            celborder=row7.createCell(3);
	            celborder.setCellValue("LUGAR:    HOSPITAL REGIONAL B TLAJOMULCO");
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            
	            Row row8 = sheet.createRow(8);
	            region = new CellRangeAddress(8, 8, 0, 2);
	            sheet.addMergedRegion(region);
	            celborder=row8.createCell(0);
	            celborder.setCellValue("DEPENDENCIA:     ISSSTE");
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            region = new CellRangeAddress(8, 8, 3, 8);
	            sheet.addMergedRegion(region);
	            celborder=row8.createCell(4);
	            celborder.setCellValue("TIPO DE NOMINA:   CHEQUES");
	            celborder.setCellStyle(headerStyle);
	            agregaMargenRegion(region,sheet);
	            
	            // Aplicar negrita a las etiquetas de metadatos de la fila 5-8
//	            for (int i = 5; i <= 8; i++) {
//	                sheet.getRow(i).getCell(0).setCellStyle(boldStyle);
//	                sheet.getRow(i).getCell(4).setCellStyle(boldStyle);
//	            }

	            // --- FILA 10: MOTIVO DE CANCELACIÓN ---
	            Row row10 = sheet.createRow(10);
	            region = new CellRangeAddress(10, 10, 5, 8);
	            sheet.addMergedRegion(region);
	            Cell cellMotivo = row10.createCell(5);
	            cellMotivo.setCellValue("MOTIVO DE LA CANCELACIÓN");
	            cellMotivo.setCellStyle(boldCenterStyle);
	            agregaMargenRegion(region,sheet);
	            // --- FILA 11: ENCABEZADOS DE LA TABLA ---
	            Row row11 = sheet.createRow(11);
	            celborder=row11.createCell(0);
	            celborder.setCellValue("NUMERO DE EMPLEADO");
	            celborder.setCellStyle(headerStyleCenter);
	            region = new CellRangeAddress(11, 11, 1, 3);
	            sheet.addMergedRegion(region);
	            celborder=row11.createCell(1);
	            celborder.setCellValue("NOMBRE");
	            celborder.setCellStyle(headerStyleCenter);
	            celborder=row11.createCell(4);
	            agregaMargenRegion(region,sheet);
	            celborder.setCellValue("NÚMERO DE COMPROBANTE");
	            celborder.setCellStyle(headerStyleCenter);
	            celborder=row11.createCell(5);
	            celborder.setCellValue("IMPORTE");
	            celborder.setCellStyle(headerStyleCenter);
	            celborder=row11.createCell(6);
	            celborder.setCellValue("CLAVE");
	            celborder.setCellStyle(headerStyleCenter);
	            region = new CellRangeAddress(11, 11, 7, 8);
	            sheet.addMergedRegion(region);
	            celborder=row11.createCell(7);
	            celborder.setCellValue("DESCRIPCIÓN");
	            celborder.setCellStyle(headerStyleCenter);
	            agregaMargenRegion(region,sheet);
//	            // Aplicar estilo de encabezado a la fila 11
//	            for (int col : new int[]{0, 1, 4, 5, 6, 7}) {
//	                row11.getCell(col).setCellStyle(headerTableStyle);
//	            }

	           int startRow = 12;
	            for (TESONESEntity teson : this.ltesones) {
	                Row row = sheet.createRow(startRow);
	                celborder=row.createCell(0);
	                celborder.setCellValue((Long) teson.getEmpleado().getId());
	                celborder.setCellStyle(detalleTabla);
	                region = new CellRangeAddress(startRow, startRow, 1, 3);
		            sheet.addMergedRegion(region);
	                celborder=row.createCell(1);
	                celborder.setCellValue((String) teson.getEmpleado().getNombreCompleto());
	                celborder.setCellStyle(detalleTabla);
	                agregaMargenRegionDetalleTable(region,sheet);
	                celborder=row.createCell(4);
	                celborder.setCellValue((String) (teson.getQuincena().getFolioQuin()!=null?teson.getQuincena().getFolioQuin():"0"));
	                celborder.setCellStyle(detalleTabla);
	                // Formato numérico para el importe
	                Cell cellImporte = row.createCell(5);
	                cellImporte.setCellValue((Double) (teson.getQuincena().getSueldoNeto()!=null?teson.getQuincena().getSueldoNeto().doubleValue():new BigDecimal("0.00").doubleValue()));
	                cellImporte.setCellStyle(detalleTabla);
	                celborder=row.createCell(6);
	                celborder.setCellValue((String) teson.getMotivoTESONEntity().getCodigo());
	                celborder.setCellStyle(detalleTabla);
	                region = new CellRangeAddress(startRow, startRow, 7, 8);
		            sheet.addMergedRegion(region);
	                celborder=row.createCell(7);
	                celborder.setCellValue((String) teson.getMotivoTESONEntity().getMotivo());
	                celborder.setCellStyle(detalleTabla);
	                agregaMargenRegionDetalleTable(region,sheet);
	                startRow++;
	            }

	            // --- FILAS DE OBSERVACIONES ---
	            int currentR = startRow + 8; // Dejar espacio vacío como la plantilla original
	            Row rowObs = sheet.createRow(currentR++);
	            rowObs.createCell(0).setCellValue("OBSERVACIÓNES:");
	            rowObs.getCell(0).setCellStyle(boldStyle);

	            Row rowNota = sheet.createRow(currentR++);
	            rowNota.createCell(0).setCellValue("NOTA:");
	            rowNota.getCell(0).setCellStyle(boldStyle);

	            // --- SECCIÓN DE FIRMAS Y DECLARACIÓN BAJO PROTESTA ---
	            Row rowDeclara1 = sheet.createRow(currentR);
	            rowDeclara1.createCell(0).setCellValue("DECLARO BAJO PROTESTA DE DECIR LA VERDAD, QUE");
	            region = new CellRangeAddress(currentR, currentR, 3, 4);
	            sheet.addMergedRegion(region);
	            rowDeclara1.createCell(3).setCellValue("RESPONSABLE DEL AREA");
	            region = new CellRangeAddress(currentR, currentR, 6, 8);
	            sheet.addMergedRegion(region);
	            rowDeclara1.createCell(6).setCellValue("PAGADOR HABILITADO");
//	            rowDeclara1.getCell(4).setCellStyle(boldCenterStyle);
//	            rowDeclara1.getCell(7).setCellStyle(boldCenterStyle);

	            Row rowDeclara2 = sheet.createRow(++currentR);
	            rowDeclara2.createCell(0).setCellValue("LOS DATOS Y FIRMAS CONTENIDAS EN ESTE FORMATO");

	            Row rowDeclara3 = sheet.createRow(++currentR);
	            rowDeclara3.createCell(0).setCellValue("SON VERIDICAS Y MANIFIESTO TENER CONOCIMIENTO DE");

	            Row rowDeclara4 = sheet.createRow(++currentR);
	            rowDeclara4.createCell(0).setCellValue("LAS SANCIONES QUE SE APLICARAN EN CASO CONTRARIO");
	            region = new CellRangeAddress(currentR, currentR, 3, 4);
	            sheet.addMergedRegion(region);
	            celFirma=rowDeclara4.createCell(3);
	            celFirma.setCellValue(this.jefe.getNombre());
	            boldCenterStyle.setFont(fuente); 
	            celFirma.setCellStyle(boldCenterStyle);
	            bordeFirma(region,sheet);
	            region = new CellRangeAddress(currentR, currentR, 6, 8);
	            sheet.addMergedRegion(region);
	            celFirma=rowDeclara4.createCell(6);
	            boldCenterStyle.setFont(fuente); 
	            celFirma.setCellValue(this.jefe.getNombre());
	            celFirma.setCellStyle(boldCenterStyle);
	            bordeFirma(region,sheet);
//	            rowDeclara4.getCell(4).setCellStyle(centerStyle);
//	            rowDeclara4.getCell(7).setCellStyle(centerStyle);

	            Row rowDeclara5 = sheet.createRow(++currentR);
	            region = new CellRangeAddress(currentR, currentR, 3, 4);
	            sheet.addMergedRegion(region);
	            rowDeclara5.createCell(3).setCellValue(this.jefe.getPuesto());
	            region = new CellRangeAddress(currentR, currentR, 6, 8);
	            sheet.addMergedRegion(region);
	            rowDeclara5.createCell(6).setCellValue(this.jefe.getPuesto());
//	            rowDeclara5.getCell(4).setCellStyle(centerStyle);
//	            rowDeclara5.getCell(7).setCellStyle(centerStyle);

	            // Estilos para el texto declaratorio de la izquierda
	            for (int i = currentR - 4; i <= currentR - 1; i++) {
	                if (sheet.getRow(i).getCell(0) != null) {
	                    sheet.getRow(i).getCell(0).setCellStyle(boldStyle);
	                }
	            }

	            // --- PIE DE PÁGINA (ELABORÓ) ---
	            currentR += 4;
	            Row rowElaboro1 = sheet.createRow(currentR++);
	            rowElaboro1.createCell(0).setCellValue("Elaboró: ADRIAN PEÑA PELAYO");
	            
	            Row rowElaboro2 = sheet.createRow(currentR++);
	            rowElaboro2.createCell(0).setCellValue("Apoyo Administrativo en Salud  A-8");
	            
	            Row rowElaboro3 = sheet.createRow(currentR);
	            rowElaboro3.createCell(0).setCellValue("C.c.p. Minutario");

	            // Autoajustar el ancho de las columnas para que todo sea legible
	            for (int i = 0; i < 8; i++) {
	                sheet.autoSizeColumn(i);
	            }
	            // Escribir el archivo final
	            try (FileOutputStream fileOut = new FileOutputStream(this.nameFile)) {
	                workbook.write(fileOut);
	                System.out.println("¡Plantilla Excel generada con éxito!");
	            }
	    }catch(Exception err) {
			err.printStackTrace();
			throw new Exception(err);
		}
	}
	private void agregaMargenRegion(CellRangeAddress region,Sheet sheet) {
		RegionUtil.setBorderTop(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.THICK, region, sheet);
	}
	private void agregaMargenRegionDetalleTable(CellRangeAddress region,Sheet sheet) {
		RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THICK, region, sheet);
		RegionUtil.setBorderRight(BorderStyle.THICK, region, sheet);
	}
	private void bordeFirma(CellRangeAddress region,Sheet sheet) {
		RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
	}
}
