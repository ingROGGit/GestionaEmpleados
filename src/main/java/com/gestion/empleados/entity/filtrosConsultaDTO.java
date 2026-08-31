package com.gestion.empleados.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class filtrosConsultaDTO {
	private String qna;
	private String rangeFecha;
	private String puesto;
	private String servicio;
	private String tipoPago;
	private String tipoContrato;
	private String status;
}
