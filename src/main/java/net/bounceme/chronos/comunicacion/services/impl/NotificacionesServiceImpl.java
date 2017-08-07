package net.bounceme.chronos.comunicacion.services.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionClienteId;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;
import net.bounceme.chronos.comunicacion.services.emisores.Emisor;
import net.bounceme.chronos.comunicacion.services.emisores.EmisorFactory;
import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;
import net.bounceme.chronos.comunicacion.utils.Finalizer;
import net.bounceme.chronos.utils.data.dao.DaoPersistence;
import net.bounceme.chronos.utils.data.exceptions.DataException;

/**
 * Implementación del servicio que gestiona las notificaciones
 * 
 * @author frederik
 *
 */
@Service(NotificacionesService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NotificacionesServiceImpl extends Finalizer implements NotificacionesService {

	private static final Logger log = Logger.getLogger(NotificacionesServiceImpl.class);

	@Autowired
	@Qualifier(AppConfig.AVISOS_REPOSITORY)
	private DaoPersistence<Aviso> avisosRepository;

	@Autowired
	@Qualifier(AppConfig.NOTIFICACIONES_REPOSITORY)
	private DaoPersistence<Notificacion> notificacionesRepository;

	@Autowired
	@Qualifier(AppConfig.TIPOS_COMUNICACION_REPOSITORY)
	private DaoPersistence<TipoComunicacion> tiposComunicacionRepository;

	@Autowired
	@Qualifier(AppConfig.MEDIOS_COMUNICACION_CLIENTE_REPOSITORY)
	private DaoPersistence<MedioComunicacionCliente> mediosComunicacionClienteRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private EmisorFactory emisorFactory;

	/*
	 * (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.NotificacionesService#notificarAviso(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Notificacion notificarAviso(Long idAviso, Long idTipoMedio) throws ServiceException {
		try {
			Aviso aviso = avisosRepository.getObject(idAviso);
			TipoComunicacion tipo = tiposComunicacionRepository.getObject(idTipoMedio);

			// Establece el aviso origen de esta notificación
			Notificacion notificacion = new Notificacion();
			notificacion.setAviso(aviso);

			// Establece el medio de comunicación
			MedioComunicacionClienteId medioComunicacionClienteId = getIdentificadorMedio(aviso.getCliente(), tipo);
			MedioComunicacionCliente medio = mediosComunicacionClienteRepository.getObject(medioComunicacionClienteId);
			notificacion.setDatosMedioComunicacion(medio);

			return notificacionesRepository.saveObject(notificacion);
		}
		catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.bounceme.chronos.comunicacion.services.NotificacionesService#prepararNotificacionParaEnvio(java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void prepararNotificacionParaEnvio(Long idNotificacion) throws ServiceException {
	    /**
	     * En lugar de realizar el envío directamente al cliente, envía el identificador de la notificación
	     * a la cola de mensajes. Posteriormente y en paralelo un receptor al otro lado de la cola
	     * recoge el identificador, obtiene la notificación y la envía
	     */
		rabbitTemplate.convertAndSend(AppConfig.QUEUE_NAME, idNotificacion.toString());
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.NotificacionesService#enviarNotificacion(java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void enviarNotificacion(Long idNotificacion) throws ServiceException {
		try {
			Notificacion notificacion = notificacionesRepository.getObject(idNotificacion);
			
			// Obtiene el medio de comunicación
			MedioComunicacionCliente medio = notificacion.getDatosMedioComunicacion();
			TipoComunicacion tipo = medio.getId().getTipoComunicacion();
			
			// Obtiene el aviso a enviar
			Aviso aviso = notificacion.getAviso();
			
			// Obtiene el emisor
			Emisor emisor = emisorFactory.getEmisor(tipo);
			
			// Construye el mensaje a enviar
			StringBuilder mensaje = new StringBuilder();
			mensaje.append(aviso.getMensaje());
			mensaje.append(" (" + aviso.getFechaInicioObra() + ")");
			
			// Envía el mensaje a través del medio seleccionado
			log.info("Notificación: " + notificacion.getId() + ": Enviando mensaje...");
			notificacion.setFechaHoraEnvio(new Date());
			ResultadoEnvio resultado = emisor.enviar(mensaje.toString(), medio.getValor());
			log.info(resultado.name());
			
			// El aviso ha sido enviado
			if (resultado.equals(ResultadoEnvio.OK)) {
				aviso.setEstaNotificado(Boolean.TRUE);
				avisosRepository.updateObject(aviso);
			}
			
			notificacion.setResultado(resultado.name());
			notificacionesRepository.updateObject(notificacion);
		}
		catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/**
	 * Método de conveniencia que construye el identificador para un medio de comunicación
	 * 
	 * @param cliente
	 * @param tipo
	 * @return
	 */
	private MedioComunicacionClienteId getIdentificadorMedio(Cliente cliente, TipoComunicacion tipo) {
		return new MedioComunicacionClienteId(cliente, tipo);
	}

}
