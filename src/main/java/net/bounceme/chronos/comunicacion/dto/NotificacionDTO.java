package net.bounceme.chronos.comunicacion.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
	
	/**
     * Fecha y hora de creación
     */
    private Date fechaHoraCreacion;
	
	/**
	 * Fecha y hora del envío
	 */
	private Date fechaHoraEnvio;
	
	/**
	 * Indica si se ha logrado enviar el aviso o no
	 */
	private String resultado;
	
	private Integer reintentos;
	
	private String estado;
	
	/**
	 * El aviso con la fecha de inicio de la obra
	 * y el medio de comunicación
	 */
	private AvisoDTO aviso;
	
	/**
     * 
     */
    private MedioComunicacionClienteDTO medioComunicacion;
}
