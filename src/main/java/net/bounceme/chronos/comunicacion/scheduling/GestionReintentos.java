package net.bounceme.chronos.comunicacion.scheduling;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * @author Federico Martín Lara
 *
 */
@Component
public class GestionReintentos {
	private static final Logger log = Logger.getLogger(GestionReintentos.class);

	@Autowired
	@Qualifier(NotificacionesService.NAME)
	private NotificacionesService notificacionesService;

	
	/**
	 * Esta tarea se ejecuta todos los dias a las 19:00 horas
	 */
	@Scheduled(cron = "0 0 19 * * *")
	public void reenvioNotificaciones() {
		try {

			List<Notificacion> notificacionesPendientes = notificacionesService.getNotificacionesNoEnviadas();

			for (Notificacion notificacion : notificacionesPendientes) {
				notificacionesService.enviarNotificacion(notificacion.getId());
			}

		} catch (ServiceException e) {
			log.error(e);
		}
	}
}
