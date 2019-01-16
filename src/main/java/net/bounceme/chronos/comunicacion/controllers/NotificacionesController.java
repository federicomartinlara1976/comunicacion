package net.bounceme.chronos.comunicacion.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsNotificacion;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionesController {
	Logger log = LoggerFactory.getLogger(NotificacionesController.class);

	@Autowired
	private NotificacionesService notificacionesService;

	/**
	 * Crea una nueva notificación
	 * 
	 * @param notificacion parámetros de creación de la notificación
	 * @return la notificación creada
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PostMapping(value = "/new")
	@ResponseStatus(HttpStatus.CREATED)
	public NotificacionDTO nuevo(@RequestBody ParamsNotificacion notificacion) throws ControllerException {
		try {
			return notificacionesService.notificarAviso(notificacion.getIdAviso(), notificacion.getIdTipoMedio());
		} catch (ServiceException e) {
			log.error("ERROR: ", e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Envia una notificación.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PutMapping(value = "/send", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void enviar(@RequestBody ParamsNotificacion notificacion) throws ControllerException {
		try {
			notificacionesService.prepararNotificacionParaEnvio(notificacion.getIdNotificacion());
		} catch (ServiceException e) {
			log.error("ERROR: ", e);
			throw new ControllerException(e);
		}
	}
}
