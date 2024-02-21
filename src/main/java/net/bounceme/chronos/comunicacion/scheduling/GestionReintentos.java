package net.bounceme.chronos.comunicacion.scheduling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;

/**
 * @author Federico Martín Lara
 *
 */
@Component
@Slf4j
public class GestionReintentos {
	
	@Autowired
	private NotificacionesService notificacionesService;

	
	/**
	 * Esta tarea se ejecuta todos los dias a las 19:00 horas
	 */
	@Scheduled(cron = "0 0 19 * * *")
	public void reenvioNotificaciones() {
		try {

			List<NotificacionDTO> notificacionesPendientes = notificacionesService.getNotificaciones("NO_ENVIADA");

			for (NotificacionDTO notificacion : notificacionesPendientes) {
				notificacionesService.enviarNotificacion(notificacion);
			}

		} catch (Exception e) {
			log.error("ERROR: ", e);
		}
	}
}
