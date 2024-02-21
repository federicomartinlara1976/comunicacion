package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;

/**
 * Interfaz del servicio que gestiona los medios de comunicación de un cliente
 * 
 * @author frederik
 *
 */
public interface MediosComunicacionClienteService {
	
	MedioComunicacionClienteDTO save(Long id, MedioComunicacionClienteDTO medioComunicacionClienteDTO);
	
	MedioComunicacionClienteDTO save(MedioComunicacionClienteDTO medioComunicacionClienteDTO);

	MedioComunicacionClienteDTO findById(Long idDireccion);
	
	MedioComunicacionClienteDTO findByClienteAndTipo(Long idCliente, String tipo);

	void delete(Long id);

	List<MedioComunicacionClienteDTO> list(Long idCliente);
}
