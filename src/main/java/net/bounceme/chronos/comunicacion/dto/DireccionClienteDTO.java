package net.bounceme.chronos.comunicacion.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DireccionClienteDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
	
	private ClienteDTO cliente;
	
	private String direccion;
	
	private String numero;
	
	private Integer piso;
	
	private String escalera;
	
	private String puerta;
	
	private String localidad;
	
	private String provincia;
	
	private String codigoPostal;
}
