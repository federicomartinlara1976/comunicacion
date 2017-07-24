package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Cliente;

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
	Cliente nuevo(String nombre, String apellidos, String dni) throws ServiceException;
	
	/**
	 * Obtiene un cliente por su identificador
	 * 
	 * @param id
	 * @return cliente
	 */
	Cliente get(Long id);
	
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
	List<Cliente> listar();
}
