package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;

public interface DireccionesClienteService {

	DireccionClienteDTO save(Long id, DireccionClienteDTO direccionClienteDTO);
	
	DireccionClienteDTO save(DireccionClienteDTO direccionClienteDTO);

	DireccionClienteDTO findById(Long idDireccion);

	void delete(Long id);

	List<DireccionClienteDTO> list(Long idCliente);
}
