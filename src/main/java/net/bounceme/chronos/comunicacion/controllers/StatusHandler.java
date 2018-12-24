package net.bounceme.chronos.comunicacion.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.bounceme.chronos.comunicacion.exceptions.ControllerException;

/**
 * Interceptador de errores de la capa de presentación
 * 
 * @author frederik
 *
 */
@ControllerAdvice
public class StatusHandler {
	
	private static final Logger log = LoggerFactory.getLogger(StatusHandler.class);

	/**
	 * Este método se dispara cuando hay un error en los parámetros de entrada.
	 * Al cliente le devuelve un error 404
	 * 
	 * @param exception
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Estado no encontrado")
	@ExceptionHandler({ IllegalArgumentException.class })
	public void handleIllegalArgumentException(Exception exception) {
		log.error("ERROR: ", exception);
	}

	/**
	 * Este método se dispara cuando hay un error en algún método de un controller.
	 * Al cliente se le devuelve un error 500
	 * 
	 * @param exception
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error interno")
	@ExceptionHandler({ ControllerException.class, MethodArgumentNotValidException.class, Exception.class })
	public void handleServiceException(Exception exception) {
		log.error("ERROR: ", exception);
	}
}
