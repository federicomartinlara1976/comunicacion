package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;

/**
 * Servicio de gestión y envío de notificaciones
 * 
 * @author frederik
 *
 */
public interface NotificacionesService {
	
    NotificacionDTO save(NotificacionDTO notificacionDTO);
    
    void prepararNotificacionParaEnvio(NotificacionDTO notificacionDTO); 
    
    void enviarNotificacion(NotificacionDTO notificacionDTO);
    
    List<NotificacionDTO> getNotificaciones(String estado);
}
