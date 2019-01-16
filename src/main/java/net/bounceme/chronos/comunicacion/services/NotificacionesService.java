package net.bounceme.chronos.comunicacion.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.emisores.Emisor;
import net.bounceme.chronos.comunicacion.services.emisores.EmisorFactory;
import net.bounceme.chronos.comunicacion.utils.Constantes.EstadoNotificacion;
import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NotificacionesService {
	private static final String ERROR = "ERROR: ";

	private static final Logger log = LoggerFactory.getLogger(NotificacionesService.class);

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
	@Qualifier(AppConfig.REGISTRO_NOTIFICACIONES_REPOSITORY)
	private DaoPersistence<RegistroNotificacion> registroNotificacionRepository;
	
	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;
	
	@Autowired
	@Qualifier("notificacionAssembler")
	private Assembler<Notificacion, NotificacionDTO> notificacionAssembler;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private EmisorFactory emisorFactory;
	
	@Value("${envio.reintentos}")
	int maxNumReintentos;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.NotificacionesService#
	 * notificarAviso(java.lang.Long, java.lang.Long)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public NotificacionDTO notificarAviso(Long idAviso, Long idTipoMedio) throws ServiceException {
		try {
			Aviso aviso = avisosRepository.getObject(idAviso);
			TipoComunicacion tipo = tiposComunicacionRepository.getObject(idTipoMedio);

			// Establece el aviso origen de esta notificación
			Notificacion notificacion = new Notificacion();
			notificacion.setAviso(aviso);

			// Establece el medio de comunicación
			MedioComunicacionCliente medio = getMedioComunicacion(aviso.getCliente(), tipo);
			notificacion.setDatosMedioComunicacion(medio);

			return notificacionAssembler.assemble(notificacionesRepository.saveObject(notificacion));
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.NotificacionesService#
	 * prepararNotificacionParaEnvio(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void prepararNotificacionParaEnvio(Long idNotificacion) throws ServiceException {
		/**
		 * En lugar de realizar el envío directamente al cliente, envía el identificador
		 * de la notificación a la cola de mensajes. Posteriormente y en paralelo un
		 * receptor al otro lado de la cola recoge el identificador, obtiene la
		 * notificación y la envía
		 */
		rabbitTemplate.convertAndSend(AppConfig.QUEUE_NAME, idNotificacion.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.NotificacionesService#
	 * enviarNotificacion(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void enviarNotificacion(Long idNotificacion) throws ServiceException {
		try {
			Notificacion notificacion = notificacionesRepository.getObject(idNotificacion);

			// Obtiene el medio de comunicación
			MedioComunicacionCliente medio = notificacion.getDatosMedioComunicacion();
			TipoComunicacion tipo = medio.getTipoComunicacion();

			// Obtiene el aviso a enviar
			Aviso aviso = notificacion.getAviso();
			Cliente cliente = aviso.getCliente();

			// Obtiene el emisor
			Emisor emisor = emisorFactory.getEmisor(tipo);

			// Construye el mensaje a enviar
			StringBuilder mensaje = buildMensaje(aviso);

			// Envía el mensaje a través del medio seleccionado
			notificacion.setFechaHoraEnvio(new Date());
			ResultadoEnvio resultado = emisor.enviar(mensaje.toString(), medio.getValor());

			// Obtiene el numero de reintentos de la notificacion
			Integer reintentos = Objects.isNull(notificacion.getReintentos()) ? 0 : notificacion.getReintentos();
			reintentos += 1;
			notificacion.setReintentos(reintentos);
			
			// El aviso ha sido enviado
			if (resultado.equals(ResultadoEnvio.OK)) {
				aviso.setEstaNotificado(Boolean.TRUE);
				notificacion.setEstado(EstadoNotificacion.ENVIADA.name());
			}
			else {
				
				aviso.setEstaNotificado(Boolean.FALSE);
				
				if (reintentos < maxNumReintentos) {
					// Si no ha llegado al número de reintentos, estado no enviada
					notificacion.setEstado(EstadoNotificacion.NO_ENVIADA.name());
				}
				else {
					// Si ha llegado al número de reintentos, estado a fallido
					notificacion.setEstado(EstadoNotificacion.FALLIDA.name());
				}
			}

			notificacion.setResultado(resultado.name());
			notificacionesRepository.updateObject(notificacion);
			avisosRepository.updateObject(aviso);
			registraNotificacion(notificacion, cliente);
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	private StringBuilder buildMensaje(Aviso aviso) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(aviso.getMensaje());
		mensaje.append(" (" + aviso.getFechaInicioObra() + ")\n");
		mensaje.append(aviso.getDireccionCliente().toString());
		return mensaje;
	}
	
	/**
	 * @param notificacion
	 * @param cliente
	 * @throws Exception
	 */
	private void registraNotificacion(Notificacion notificacion, Cliente cliente) throws Exception {
		RegistroNotificacion registroNotificacion = new RegistroNotificacion();
		registroNotificacion.setNotificacion(notificacion);
		registroNotificacion.setCliente(cliente);
		registroNotificacion.setResultado(notificacion.getResultado());
		registroNotificacion.setFechaHoraNotificacion(notificacion.getFechaHoraEnvio());
		
		registroNotificacionRepository.saveObject(registroNotificacion);
	}

	@SuppressWarnings("unchecked")
	public List<NotificacionDTO> getNotificacionesNoEnviadas() {
		try {
			return new ArrayList<>(notificacionAssembler.assemble(
					daoQueries.executeNamedQuery("notificacionesPendientes", Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}

	/**
	 * @param cliente
	 * @param tipo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MedioComunicacionCliente getMedioComunicacion(Cliente cliente, TipoComunicacion tipo) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cliente", cliente);
		parameters.put("tipo", tipo);
		Optional<MedioComunicacionCliente> oResult = daoQueries.executeScalarNamedQuery("medioComunicacionCliente", parameters,
				Boolean.TRUE);
		
		return (oResult.isPresent()) ? oResult.get() : null;
	}
}