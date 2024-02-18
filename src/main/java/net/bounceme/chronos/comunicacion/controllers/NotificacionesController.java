package net.bounceme.chronos.comunicacion.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/notificaciones")
@Slf4j
public class NotificacionesController {
	
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
	public ResponseEntity<?> nuevo(@RequestBody NotificacionDTO notificacion) {
		Map<String, Object> response = new HashMap<>();

		notificacion = notificacionesService.save(notificacion);

		log.info("Creada: {}", notificacion.toString());
		response.put("mensaje", "La notificación ha sido creada con éxito");
		response.put("notificacion", notificacion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 * Envia una notificación.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PutMapping(value = "/send")
	public void enviar(@RequestBody NotificacionDTO notificacion) {
		notificacionesService.prepararNotificacionParaEnvio(notificacion);
	}
}
