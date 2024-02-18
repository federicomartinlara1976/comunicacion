package net.bounceme.chronos.comunicacion.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad cliente
 * 
 * @author frederik
 *
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;

	private String nombre;

	private String apellidos;

	private String dni;

	private List<AvisoDTO> avisos;

	private List<MedioComunicacionClienteDTO> mediosComunicacion;
	
	private List<DireccionClienteDTO> direcciones;
}
