package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;

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
	TipoComunicacion nuevo(String denominacion, String nombreEmisor) throws ServiceException;
	
	/**
	 * Obtiene un tipo de comunicación
	 * 
	 * @param id
	 * @return el tipo
	 */
	TipoComunicacion get(Long id);
	
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
	List<TipoComunicacion> listar();
}
