package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;

/**
 * Interfaz del servicio de gestión de clientes
 * 
 * @author frederik
 *
 */
public interface ClientesService {
	public static final String NAME = "clientesService";
	
	/**
	 * Crea un cliente nuevo
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @return el cliente creado
	 * @throws ServiceException 
	 */
	ClienteDTO nuevo(String nombre, String apellidos, String dni) throws ServiceException;
	
	/**
	 * Obtiene un cliente por su identificador
	 * 
	 * @param id
	 * @return cliente
	 */
	ClienteDTO get(Long id);
	
	/**
	 * Actualiza los datos de un cliente
	 * 
	 * @param id
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @throws ServiceException 
	 */
	void actualizar(Long id, String nombre, String apellidos, String dni) throws ServiceException;

	/**
	 * Borra un cliente
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	void borrar(Long id) throws ServiceException;
	
	/**
	 * Listado de todos los clientes
	 * 
	 * @return listado de clientes
	 */
	List<ClienteDTO> listar();
	
	/**
	 * @param nombre
	 * @return
	 */
	List<ClienteDTO> buscarPorNombre(String nombre);
	
	/**
	 * @param apellidos
	 * @return
	 */
	List<ClienteDTO> buscarPorApellidos(String apellidos);
	
	/**
	 * @param nombre
	 * @param apellidos
	 * @return
	 */
	List<ClienteDTO> buscarPorNombreYApellidos(String nombre, String apellidos);
	
	/**
	 * @param dni
	 * @return
	 */
	List<ClienteDTO> buscarPorDNI(String dni);
}
