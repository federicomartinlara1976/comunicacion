package net.bounceme.chronos.comunicacion.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class AvisoDTO implements Serializable {

	
	/**
     * 
     */
    private static final long serialVersionUID = -6588207037035821635L;

    private Long id;

	private Date fechaInicioObra;
	
	private String mensaje;
	
	private Boolean estaNotificado;
	
	private ClienteDTO cliente;

	private DireccionClienteDTO direccionCliente;
	
	private List<NotificacionDTO> notificaciones;
}
