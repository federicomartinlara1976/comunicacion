package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.exceptions.DataException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;

/**
 * Interfaz del servicio que gestiona los medios de comunicación de un cliente
 * 
 * @author frederik
 *
 */
public interface MediosComunicacionClienteService {
	public static final String NAME = "mediosComunicacionClienteService";
	
	/**
	 * Crea un medio de comunicación (un número de FAX, móvil o email)
	 * 
	 * @param idCliente identificador del cliente
	 * @param idTipo tipo de medio de comunicación
	 * @param valor
	 * @return Medio de comunicación creado
	 * @throws DataException
	 */
	MedioComunicacionCliente nuevo(Long idCliente, Long idTipo, String valor) throws ServiceException;

	/**
	 * Obtiene un medio de comunicación de un cliente
	 * 
	 * @param idCliente identificador del cliente
     * @param idTipo tipo de medio de comunicación
	 * @return Medio de comunicación
	 */
	MedioComunicacionCliente get(Long idCliente, Long idTipo);

	/**
	 * Actualiza un medio de comunicación de un cliente
	 * 
	 * @param idCliente identificador del cliente
     * @param idTipo tipo de medio de comunicación
	 * @param valor
	 * @throws DataException
	 */
	void actualizar(Long idCliente, Long idTipo, String valor) throws ServiceException;

	/**
	 * Borra un medio de comunicación de un cliente
	 * 
	 * @param idCliente identificador del cliente
     * @param idTipo tipo de medio de comunicación
	 * @throws DataException
	 */
	void borrar(Long idCliente, Long idTipo) throws ServiceException;
	
	/**
	 * Lista todos los medios de comunicación de un cliente
	 * 
	 * @param idCliente
	 * @return
	 */
	List<MedioComunicacionCliente> listar(Long idCliente);
}
