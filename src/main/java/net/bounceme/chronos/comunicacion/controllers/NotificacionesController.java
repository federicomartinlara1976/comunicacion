package net.bounceme.chronos.comunicacion.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsNotificacion;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {

	@Autowired
	@Qualifier(NotificacionesService.NAME)
	private NotificacionesService notificacionesService;
	
	/**
	 * Crea una nueva notificación
	 * 
	 * @param notificacion parámetros de creación de la notificación
	 * @return la notificación creada
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(
			value="/new", 
			method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Notificacion nuevo(
			@RequestBody ParamsNotificacion notificacion) throws ControllerException {
			return notificacionesService.notificarAviso(notificacion.getIdAviso(), notificacion.getIdTipoMedio());
		
	}
	
	/**
	 * Envia una notificación.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value="/send", 
			method = RequestMethod.PUT,
			consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void enviar(
			@RequestBody ParamsNotificacion notificacion) throws ControllerException {
		try {
			notificacionesService.prepararNotificacionParaEnvio(notificacion.getIdNotificacion());
		} catch (ServiceException exception) {
			throw new ControllerException(exception);
		}
	}
}
