package net.bounceme.chronos.comunicacion.controllers;

import javax.validation.Valid;

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

import net.bounceme.chronos.comunicacion.controllers.params.ParamsAviso;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.services.AvisosService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/avisos")
public class AvisosController {

	@Autowired
	@Qualifier(AvisosService.NAME)
	private AvisosService avisosService;

	/**
	 * Crea un nuevo aviso
	 * 
	 * @param aviso
	 *            parámetros de creación del aviso
	 * @return el aviso creado
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Aviso nuevo(@RequestBody @Valid ParamsAviso aviso) throws ControllerException {
		return avisosService.nuevoAviso(aviso.getIdCliente(), aviso.getFechaInicioObra(), aviso.getMensaje());
	}

	/**
	 * Obtiene un aviso
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Aviso> get(@PathVariable Long id) {
		Aviso aviso = avisosService.get(id);
		HttpStatus status = aviso != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Aviso>(aviso, status);
	}

	/**
	 * Borra un aviso
	 * 
	 * @param id
	 *            identificador
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void anular(@PathVariable Long id) throws ControllerException {
		// Si tiene notificaciones enviadas, no se podrá borrar
		avisosService.anularAviso(id);
	}
}
