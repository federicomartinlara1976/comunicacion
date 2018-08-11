package net.bounceme.chronos.comunicacion.jms;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * Receptor de la cola de mensajes. Se usa para evitar que la aplicación se
 * quede bloqueada mientras efectúa el envío de una notificación de aviso
 * 
 * @author frederik
 *
 */
@Component
public class Receiver {

	private static final Logger log = Logger.getLogger(Receiver.class);

	@Autowired
	@Qualifier(NotificacionesService.NAME)
	private NotificacionesService notificacionesService;

	/**
	 * El método receptor
	 * 
	 * @param message
	 */
	public void receiveMessage(String message) {
		try {
			log.info("Received <" + message + ">");
			notificacionesService.enviarNotificacion(Long.valueOf(message));
		} catch (ServiceException e) {
			log.error(e);
		}
	}
}
