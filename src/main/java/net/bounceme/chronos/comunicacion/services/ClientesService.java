package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.ClienteDTO;

/**
 * Interfaz del servicio de gestión de clientes
 * 
 * @author frederik
 *
 */
public interface ClientesService {
	
	ClienteDTO save(ClienteDTO clienteDTO);
	
	ClienteDTO findById(Long id);

	void delete(Long id);
	
	List<ClienteDTO> list();
	
	List<ClienteDTO> findByNombre(String nombre);
	
	List<ClienteDTO> findByApellidos(String apellidos);
	
	List<ClienteDTO> findByNombreAndApellidos(String nombre, String apellidos);
	
	ClienteDTO findByDNI(String dni);
}
