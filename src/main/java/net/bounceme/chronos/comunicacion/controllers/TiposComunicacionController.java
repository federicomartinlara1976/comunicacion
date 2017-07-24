package net.bounceme.chronos.comunicacion.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/tiposComunicacion")
public class TiposComunicacionController {

	private static final Logger log = Logger.getLogger(TiposComunicacionController.class);
	
	@Autowired
	@Qualifier(TiposComunicacionService.NAME)
	private TiposComunicacionService tiposComunicacionService;
	
	/**
	 * Devuelve un listado de los tipos de comunicación
	 * 
	 * @return listado
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public List<TipoComunicacion> listAll() {
		return tiposComunicacionService.listar();
	}
	
	/**
	 * Crea un nuevo tipo de comunicación
	 * 
	 * @param denominacion nombre del tipo
	 * @return el tipo creado
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(
			value="/new", 
			method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public TipoComunicacion nuevo(
			@RequestBody TipoComunicacion tipo) throws ControllerException {
		try {
			return tiposComunicacionService.nuevo(tipo.getDenominacion(), tipo.getNombreClaseEmisora());
		} catch (ServiceException exception) {
			log.error(exception);
			throw new ControllerException(exception);
		}
	}
	
	/**
	 * Obtiene un tipo de comunicación
	 * 
	 * @param id identificador
	 * @return el tipo
	 */ 
	@CrossOrigin
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<TipoComunicacion> get(@PathVariable Long id) {
		TipoComunicacion tipoComunicacion = tiposComunicacionService.get(id);
		HttpStatus status = tipoComunicacion != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<TipoComunicacion>(tipoComunicacion, status);
	}
	
	/**
	 * Actualiza un tipo de comunicación
	 * 
	 * @param id identificador
	 * @param tipo Los datos modificados del tipo (denominacion)
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void actualizar(
			@PathVariable Long id,
			@RequestBody TipoComunicacion tipo) throws ControllerException {
		try {
			tiposComunicacionService.actualizar(id, tipo.getDenominacion(), tipo.getNombreClaseEmisora());
		} catch (ServiceException exception) {
			log.error(exception);
			throw new ControllerException(exception);
		}
	}
	
	/**
	 * Borra un tipo de comunicación
	 * 
	 * @param id identificador
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value="/{id}/delete", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void borrar(@PathVariable Long id) throws ControllerException {
		try {
			tiposComunicacionService.borrar(id);
		} catch (ServiceException exception) {
			log.error(exception);
			throw new ControllerException(exception);
		}
	}
}
