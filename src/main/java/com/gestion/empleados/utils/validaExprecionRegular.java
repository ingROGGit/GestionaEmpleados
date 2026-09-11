package com.gestion.empleados.utils;

public class validaExprecionRegular {
	public static boolean validarCurp(String curp) {
        String regex = "^[A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|BC|CC|CS|CH|CL|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d]\\d$";
        return curp != null && curp.toUpperCase().matches(regex);
    }
	public static boolean validarRFC(String rfc) {
		// Expresión regular adaptada para Strings de Java
        String regex = "^[A-ZÑ&]{3,4}\\\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\\\d|3[01])[A-Z\\\\d]{2}[A\\\\d]$";
        
        // Se recomienda convertir a mayúsculas y quitar espacios/guiones antes de validar
        if (rfc == null) return false;
        String rfcLimpio = rfc.trim().toUpperCase();
        
        return rfcLimpio.matches(regex);
    }
}
