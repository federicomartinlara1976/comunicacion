package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;

/**
 * Interfaz del servicio que gestiona los tipos de comunicación 
 * 
 * @author frederik
 *
 */
public interface TiposComunicacionService {
	public static final String NAME = "tiposComunicacionService";
	
	/**
	 * Crea un tipo de comunicación
	 * 
	 * @param denominacion
	 * @nombreEmisor nombre del bean emisor
	 * @return el tipo creado
	 * @throws ServiceException 
	 */
	TipoComunicacionDTO nuevo(String denominacion, String nombreEmisor) throws ServiceException;
	
	/**
	 * Obtiene un tipo de comunicación
	 * 
	 * @param id
	 * @return el tipo
	 */
	TipoComunicacionDTO get(Long id);
	
	/**
	 * Obtiene un tipo de comunicación
	 * 
	 * @param name
	 * @return el tipo
	 */
	TipoComunicacionDTO get(String name);
	
	/**
	 * Actualiza un tipo de comunicación
	 * 
	 * @param id
	 * @param nuevaDenominacion
	 * @param nuevoEmisor
	 * @throws ServiceException 
	 */
	void actualizar(Long id, String nuevaDenominacion, String nuevoEmisor) throws ServiceException;
	
	/**
	 * Borra un tipo de comunicación
	 * 
	 * @param id
	 * @throws ServiceException 
	 */
	void borrar(Long id) throws ServiceException;
	
	/**
	 * Lista todos los tipos de comunicación
	 * 
	 * @return
	 */
	List<TipoComunicacionDTO> listar();
}
