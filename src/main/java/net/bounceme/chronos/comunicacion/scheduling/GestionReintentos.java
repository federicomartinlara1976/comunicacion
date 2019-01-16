package net.bounceme.chronos.comunicacion.scheduling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * @author Federico Martín Lara
 *
 */
@Component
public class GestionReintentos {
	private static final Logger log = LoggerFactory.getLogger(GestionReintentos.class);

	@Autowired
	private NotificacionesService notificacionesService;

	
	/**
	 * Esta tarea se ejecuta todos los dias a las 19:00 horas
	 */
	@Scheduled(cron = "0 0 19 * * *")
	public void reenvioNotificaciones() {
		try {

			List<NotificacionDTO> notificacionesPendientes = notificacionesService.getNotificacionesNoEnviadas();

			for (NotificacionDTO notificacion : notificacionesPendientes) {
				notificacionesService.enviarNotificacion(notificacion.getId());
			}

		} catch (ServiceException e) {
			log.error("ERROR: ", e);
		}
	}
}
