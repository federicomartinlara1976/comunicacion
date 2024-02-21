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
public class MedioComunicacionClienteDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
	
	private TipoComunicacionDTO tipoComunicacion;
	
	private ClienteDTO cliente;

	private String valor;
}
