package com.gestion.empleados.utils;

public class WorkDates {

	public String getMM(String Mes) {
		String DD="";
		switch(Mes) {
		case "ENERO":
			DD="01";
			break;
		case "FEBRERO":
			DD="02";
			break;
		case "MARZO":
			DD="03";
			break;
		case "ABRIL":
			DD="04";
			break;
		case "MAYO":
			DD="05";
			break;
		case "JUNIO":
			DD="06";
			break;
		case "JULIO":
			DD="07";
			break;
		case "AGOSTO":
			DD="08";
			break;
		case "SEPTIEMBRE":
			DD="09";
			break;
		case "OCTUBRE":
			DD="10";
			break;
		case "NOVIEMBRE":
			DD="11";
			break;
		case "DICIEMBRE":
			DD="12";
			break;
		}
		return DD;
	}
}
