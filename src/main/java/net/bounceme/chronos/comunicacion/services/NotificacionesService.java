package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;

/**
 * Servicio de gestión y envío de notificaciones
 * 
 * @author frederik
 *
 */
public interface NotificacionesService {
	public static final String NAME = "notificacionesService";
	
    /**
     * Notifica un aviso según el medio escogido
     * 
     * @param idAviso El identificador del aviso
     * @param idTipoMedio El identificador del medio escogido
     * @return la notificación creada
     * @throws ServiceException 
     */
    NotificacionDTO notificarAviso(Long idAviso, Long idTipoMedio) throws ServiceException;
    
    /**
     * Prepara la notificación para el envío (La pone en una cola de envíos para no tener que esperar la respuesta)
     * 
     * @param idNotificacion
     * @throws ServiceException
     */
    void prepararNotificacionParaEnvio(Long idNotificacion) throws ServiceException; 
    
    /**
     * Envía una notificación
     * 
     * @param idNotificacion
     * @throws ServiceException 
     */
    void enviarNotificacion(Long idNotificacion) throws ServiceException;
    
    /**
     * @return
     */
    List<NotificacionDTO> getNotificacionesNoEnviadas();
}
