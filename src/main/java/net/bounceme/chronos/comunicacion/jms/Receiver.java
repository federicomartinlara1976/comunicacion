package net.bounceme.chronos.comunicacion.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * Receptor de la cola de mensajes. Se usa para evitar que la aplicación se
 * quede bloqueada mientras efectúa el envío de una notificación de aviso
 * 
 * @author frederik
 *
 */
@Component
@Slf4j
public class Receiver {

	@Autowired
	private NotificacionesService notificacionesService;

	/**
	 * El método receptor
	 * 
	 * @param message
	 */
	@Transactional(readOnly = true)
	public void receiveMessage(String message) {
		log.info("Received <{}>", message);
		NotificacionDTO notificacion = notificacionesService.findById(Long.valueOf(message));
		notificacionesService.enviarNotificacion(notificacion);
	}
}
