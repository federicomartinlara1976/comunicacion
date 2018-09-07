package net.bounceme.chronos.comunicacion.services;

import java.util.Date;
import java.util.List;

import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;

/**
 * Servicio de gestión y envío de notificaciones
 * 
 * @author frederik
 *
 */
public interface RegistroNotificacionesService {
	public static final String NAME = "registroNotificacionesService";
	
    /**
     * @param from
     * @param to
     * @return
     */
    List<RegistroNotificacion> searchByDates(Date from, Date to);
    
    List<RegistroNotificacion> searchByClient(Long idClient);
}
