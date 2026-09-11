package com.gestion.empleados.utils.reports;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.gestion.empleados.entity.Empleados;

public class ExportCuentasBanco{
	private String tipo;
	private XSSFWorkbook libro;
	private List<Empleados> lempleados;
	private String nameFile,quinCatSelect;
	public ExportCuentasBanco(String nameFile ,List<Empleados> lempleados,String quinCatSelect) {
		this.libro = new XSSFWorkbook();
		this.lempleados = lempleados;
		this.nameFile=nameFile;
		this.quinCatSelect=quinCatSelect;
	}
	public void run() throws Exception{
		try {
				Workbook workbook = new XSSFWorkbook();
	            Sheet sheet = workbook.createSheet("Cuentas Bancarias");

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
	            headerStyle.setAlignment(HorizontalAlignment.CENTER);
	            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	            headerStyle.setBorderBottom(BorderStyle.THIN);
	            headerStyle.setBorderTop(BorderStyle.THIN);
	            headerStyle.setBorderLeft(BorderStyle.THIN);
	            headerStyle.setBorderRight(BorderStyle.THIN);

	            // Estilo para las instrucciones de captura (cursiva)
	            CellStyle italicStyle = workbook.createCellStyle();
	            Font italicFont = workbook.createFont();
	            italicFont.setItalic(true);
	            italicFont.setFontHeightInPoints((short) 9);
	            italicStyle.setFont(italicFont);
	            // 2. Leer la imagen 1
	            InputStream is = getClass().getClassLoader().getResourceAsStream("ISSSTE.png");
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
	            pict.resize();
	            
	            // 2. Leer la imagen 2
	            is = getClass().getClassLoader().getResourceAsStream("logo-ISSSTE.png");
	            bytes = IOUtils.toByteArray(is);
	            is.close();

	            // 3. Añadir la imagen al libro de trabajo y obtener el índice
	            // Puedes cambiar PICTURE_TYPE_PNG por PICTURE_TYPE_JPEG según tu archivo
	            pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

	            // 4. Crear el objeto drawing (es el contenedor de las imágenes)
	            helper = workbook.getCreationHelper();
	            drawing = sheet.createDrawingPatriarch();

	            // 5. Configurar la posición (Ancla) de la imagen
	            anchor = helper.createClientAnchor();
	            
	            // Definir la celda de origen (Esquina superior izquierda)
	            anchor.setCol1(18); // Columna B (las columnas empiezan en 0)
	            anchor.setRow1(0); // Fila 2 (las filas empiezan en 0)

	            // 6. Crear la imagen en la posición indicada
	            pict = drawing.createPicture(anchor, pictureIdx);

	            // 7. Auto-ajustar la imagen a su tamaño original
	            pict.resize();
	            // --- 2. CREACIÓN DE LA CABECERA ---
	            // Fila 5: Centro de Trabajo
	            Row row5 = sheet.createRow(5);
	            Cell cellCt = row5.createCell(2); // Columna C
	            cellCt.setCellValue("CENTRO DE TRABAJO: 04959");
	            cellCt.setCellStyle(boldCenterStyle);

	            // Fila 6: Operativo y Mando
	            Row row6 = sheet.createRow(6);
	            Cell cellOp = row6.createCell(2);
	            cellOp.setCellValue("OPERATIVO Y MANDO");
	            cellOp.setCellStyle(boldCenterStyle);

	            // Fila 7: Aplicación QNA y Tipo Empleado
	            Row row7 = sheet.createRow(7);
	            row7.createCell(0).setCellValue("APLICACIÓN:"+this.quinCatSelect);
	            row7.createCell(3).setCellValue("TIPO EMPLEADO:");

	            // Fila 9: Título centralizado de identificación
	            Row row9 = sheet.createRow(9);
	            row9.createCell(0).setCellValue("AN'T: C.P SONIA MANZANO GIL");
	            sheet.addMergedRegion(new CellRangeAddress(9, 9, 3, 10)); // Combinar para el título
	            Cell cellTitulo = row9.createCell(3);
	            cellTitulo.setCellValue("DATOS PARA IDENTIFICACIÓN DE CUENTA BANCARIA");
	            cellTitulo.setCellStyle(boldCenterStyle);

	            // Fila 10: Puesto
	            Row row10 = sheet.createRow(10);
	            row10.createCell(0).setCellValue("JEFA DE SERVICIOS DE INFORMATICA");

	            // Fila 12: Modalidad
	            Row row12 = sheet.createRow(12);
	            row12.createCell(0).setCellValue("MODALIDAD DE PAGO");

	            // --- 3. ENCABEZADOS DE LA TABLA ---
	            // Fila 13: Nombres de columnas
	            Row row13 = sheet.createRow(13);
	            String[] headers = {"NO.", "NO. DE EMPLEADO", "NOMBRE COMPLETO", "R.F.C."};
	            for (int i = 0; i < headers.length; i++) {
	                Cell cell = row13.createCell(i);
	                cell.setCellValue(headers[i]);
	                cell.setCellStyle(headerStyle);
	            }

	            // Combinar desde la columna E (4) hasta la V (21) para "NÚMERO DE CUENTA BANCARIA"
	            sheet.addMergedRegion(new CellRangeAddress(13, 13, 4, 21));
	            Cell cellCuentaBancaria = row13.createCell(4);
	            cellCuentaBancaria.setCellValue("NUMERO DE CUENTA BANCARIA");
	            cellCuentaBancaria.setCellStyle(headerStyle);

	            // Columna W (22) para el Banco
	            Cell cellBancoHeader = row13.createCell(22);
	            cellBancoHeader.setCellValue("BANCO");
	            cellBancoHeader.setCellStyle(headerStyle);

	            // Fila 14: Instrucciones de captura (debajo de número de cuenta)
	            Row row14 = sheet.createRow(14);
	            sheet.addMergedRegion(new CellRangeAddress(14, 14, 4, 21));
	            Cell cellInstrucciones = row14.createCell(4);
	            cellInstrucciones.setCellValue("Instrucciones de captura: 18 números, CLABE-interbancaria.");
	            cellInstrucciones.setCellStyle(italicStyle);
	            int renglon=1,startRow=15;
	            for (Empleados emp:this.lempleados) {
	                Row row = sheet.createRow(startRow++);
	                
	                // Columnas base
	                row.createCell(0).setCellValue((int) renglon);
	                renglon++;
	                row.createCell(1).setCellValue(emp.getId());
	                row.createCell(2).setCellValue(emp.getNombreCompleto());
	                row.createCell(3).setCellValue(emp.getRfc());

	                // Desglosar la CLABE de 18 dígitos celda por celda (columnas 4 a 21)
	                String clabe = emp.getLCuentas().get(0).getCuenta();
	                for (int i = 0; i < clabe.length(); i++) {
	                    // Convertimos cada char a entero o string individual
	                    row.createCell(4 + i).setCellValue(Character.getNumericValue(clabe.charAt(i)));
	                }

	                // Columna del Banco (Columna 22)
	                row.createCell(22).setCellValue(emp.getLCuentas().get(0).getBancoE().getBanco());
	            }


	            // --- 5. SECCIÓN DE FIRMAS (PIE DE PÁGINA) ---
	            int signatureRowStart = startRow + 3; // Dejar unas filas de espacio
	            
	            Row rowAuth = sheet.createRow(signatureRowStart);
	            rowAuth.createCell(2).setCellValue("AUTORIZA");
	            rowAuth.getCell(2).setCellStyle(boldCenterStyle);

	            Row rowName = sheet.createRow(signatureRowStart + 4); // Espacio para la firma física
	            rowName.createCell(2).setCellValue("LIC. JUDITH SUAZO OROZCO");
	            rowName.getCell(2).setCellStyle(boldCenterStyle);

	            Row rowCharge = sheet.createRow(signatureRowStart + 5);
	            rowCharge.createCell(2).setCellValue("SUPERVISOR DE NÓMINA");
	            rowCharge.getCell(2).setCellStyle(boldCenterStyle);


	            // --- 6. AUTOAJUSTE DE COLUMNAS PRINCIPALES ---
	            sheet.autoSizeColumn(1); // Id Empleado
	            sheet.autoSizeColumn(2); // Nombre
	            sheet.autoSizeColumn(3); // RFC
	            sheet.autoSizeColumn(22); // Banco
	            // Las columnas de los dígitos individuales se pueden dejar con un ancho fijo pequeño
	            for (int i = 4; i <= 21; i++) {
	                sheet.setColumnWidth(i, 3 * 256); 
	            }

	            // Escribir el archivo final
	            try (FileOutputStream fileOut = new FileOutputStream(this.nameFile+".xlsx")) {
	                workbook.write(fileOut);
	                System.out.println("¡Plantilla Excel generada con éxito!");
	            }
	    }catch(Exception err) {
			err.printStackTrace();
			throw new Exception(err);
		}
	}
}
