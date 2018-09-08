package net.bounceme.chronos.comunicacion.controllers;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsRegistroNotificacion;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.services.RegistroNotificacionesService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/registro/notificaciones")
public class RegistroNotificacionesController {
	Logger log = Logger.getLogger(RegistroNotificacionesController.class);

	@Autowired
	@Qualifier(RegistroNotificacionesService.NAME)
	private RegistroNotificacionesService registroNotificacionesService;

	
	@CrossOrigin
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<RegistroNotificacion> listarPorCliente(@PathVariable Long id) {
		return registroNotificacionesService.searchByClient(id);
	}

	/**
	 * Envia una notificación.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/fecha", method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<RegistroNotificacion> listarPorFecha(@RequestBody ParamsRegistroNotificacion registroNotificacion) {
		Date from = registroNotificacion.getFrom();
		Date to = registroNotificacion.getTo();
		
		return registroNotificacionesService.searchByDates(from, to);
	}
}
