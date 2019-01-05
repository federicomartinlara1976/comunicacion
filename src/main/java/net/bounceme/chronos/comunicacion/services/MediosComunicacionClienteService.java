package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;

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
	 * @throws ServiceException 
	 */
	MedioComunicacionClienteDTO nuevo(Long idCliente, Long idTipo, String valor) throws ServiceException;

	/**
	 * Obtiene un medio de comunicación de un cliente
	 * 
	 * @param idCliente identificador del cliente
     * @param idTipo tipo de medio de comunicación
	 * @return Medio de comunicación
	 */
	MedioComunicacionClienteDTO get(Long idCliente, Long idTipo);

	/**
	 * Actualiza un medio de comunicación de un cliente
	 * 
	 * @param idCliente identificador del cliente
     * @param idTipo tipo de medio de comunicación
	 * @param valor
	 * @throws ServiceException 
	 */
	void actualizar(Long idCliente, Long idTipo, String valor) throws ServiceException;

	/**
	 * Borra un medio de comunicación de un cliente
	 * 
	 * @param idCliente identificador del cliente
     * @param idTipo tipo de medio de comunicación
	 * @throws ServiceException 
	 */
	void borrar(Long idCliente, Long idTipo) throws ServiceException;
	
	/**
	 * Lista todos los medios de comunicación de un cliente
	 * 
	 * @param idCliente
	 * @return
	 */
	List<MedioComunicacionClienteDTO> listar(Long idCliente);
}
