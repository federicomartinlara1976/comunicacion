package net.bounceme.chronos.comunicacion.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private static final String ERROR = "ERROR: ";

	Logger log = LoggerFactory.getLogger(TiposComunicacionController.class);

	@Autowired
	@Qualifier(TiposComunicacionService.NAME)
	private TiposComunicacionService tiposComunicacionService;

	/**
	 * Devuelve un listado de los tipos de comunicación
	 * 
	 * @return listado
	 */
	@CrossOrigin
	@GetMapping
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
	@PostMapping(value = "/new")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoComunicacion nuevo(@RequestBody TipoComunicacion tipo) throws ControllerException {
		try {
			return tiposComunicacionService.nuevo(tipo.getDenominacion(), tipo.getNombreClaseEmisora());
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Obtiene un tipo de comunicación
	 * 
	 * @param id identificador
	 * @return el tipo
	 */
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<TipoComunicacion> get(@PathVariable Long id) {
		TipoComunicacion tipoComunicacion = tiposComunicacionService.get(id);
		HttpStatus status = tipoComunicacion != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(tipoComunicacion, status);
	}

	/**
	 * Actualiza un tipo de comunicación
	 * 
	 * @param id   identificador
	 * @param tipo Los datos modificados del tipo (denominacion)
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PutMapping(value = "/{id}/update")
	@ResponseStatus(HttpStatus.OK)
	public void actualizar(@PathVariable Long id, @RequestBody TipoComunicacion tipo) throws ControllerException {
		try {
			tiposComunicacionService.actualizar(id, tipo.getDenominacion(), tipo.getNombreClaseEmisora());
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Borra un tipo de comunicación
	 * 
	 * @param id identificador
	 * @throws ControllerException
	 */
	@CrossOrigin
	@DeleteMapping(value = "/{id}/delete")
	@ResponseStatus(HttpStatus.OK)
	public void borrar(@PathVariable Long id) throws ControllerException {
		try {
			tiposComunicacionService.borrar(id);
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}
}
