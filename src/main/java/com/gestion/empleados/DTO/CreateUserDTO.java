package com.gestion.empleados.DTO;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
	@NotBlank
	private Long id;
	@NotBlank
    private String usu;
    @NotBlank
    private String pass;
    @NotBlank
    private String rePass;
    @NotBlank
    private Boolean bloqueado;
    @NotBlank
    private Boolean disabled;
    private Set<String> roles;
}
