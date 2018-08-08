package net.bounceme.chronos.comunicacion.services;

import java.util.List;

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
	 */
	Cliente nuevo(String nombre, String apellidos, String dni);
	
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
	 */
	void actualizar(Long id, String nombre, String apellidos, String dni);

	/**
	 * Borra un cliente
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	void borrar(Long id);
	
	/**
	 * Listado de todos los clientes
	 * 
	 * @return listado de clientes
	 */
	List<Cliente> listar();
}
