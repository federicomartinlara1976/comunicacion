package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.AvisoRepository;
import net.bounceme.chronos.comunicacion.data.dao.NotificacionRepository;
import net.bounceme.chronos.comunicacion.data.dao.RegistroNotificacionRepository;
import net.bounceme.chronos.comunicacion.data.model.Aviso;
import net.bounceme.chronos.comunicacion.data.model.Cliente;
import net.bounceme.chronos.comunicacion.data.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.data.model.Notificacion;
import net.bounceme.chronos.comunicacion.data.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.services.EmailService;
import net.bounceme.chronos.comunicacion.services.NotificacionesService;
import net.bounceme.chronos.comunicacion.utils.Constantes.EstadoNotificacion;
import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona las notificaciones
 * 
 * @author frederik
 *
 */
@Service
@Slf4j
public class NotificacionesServiceImpl implements NotificacionesService {

	private static final String MENSAJE_FORMAT = "%s (%s)\n%s";
	
	@Value("${application.envio.asunto}")
	private String asunto;

	@Autowired
	private AvisoRepository avisoRepository;

	@Autowired
	private NotificacionRepository notificacionRepository;

	@Autowired
	private RegistroNotificacionRepository registroNotificacionRepository;

	@Autowired
	@Qualifier("notificacionAssembler")
	private BidirectionalAssembler<Notificacion, NotificacionDTO> notificacionAssembler;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private EmailService emailService;

	@Value("${application.envio.reintentos}")
	int maxNumReintentos;

	@Override
	@Transactional
	@SneakyThrows
	public NotificacionDTO save(NotificacionDTO notificacionDTO) {
		try {
			Notificacion notificacion = notificacionAssembler.reverseAssemble(notificacionDTO);
			notificacionRepository.save(notificacion);
			return notificacionAssembler.assemble(notificacion);
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	public void prepararNotificacionParaEnvio(NotificacionDTO notificacionDTO) {
		rabbitTemplate.convertAndSend(AppConfig.QUEUE_NAME, notificacionDTO.getId());
	}

	@Override
	@Transactional
	@SneakyThrows
	public void enviarNotificacion(NotificacionDTO notificacionDTO) {
		try {
			Notificacion notificacion = notificacionAssembler.reverseAssemble(notificacionDTO);

			// Obtiene el medio de comunicación
			MedioComunicacionCliente medio = notificacion.getMedioComunicacionCliente();

			// Obtiene el aviso a enviar
			Aviso aviso = notificacion.getAviso();
			Cliente cliente = aviso.getCliente();

			// Construye el mensaje a enviar
			String mensaje = String.format(MENSAJE_FORMAT, aviso.getMensaje(), aviso.getFechaInicioObra(),
					aviso.getDireccionCliente().toString());

			// Envía el mensaje a través del medio seleccionado
			notificacion.setFechaHoraEnvio(new Date());
			Boolean resultado = emailService.sendSimpleMessage(medio.getValor(), asunto, mensaje);
			
			// Obtiene el numero de reintentos de la notificacion
			Integer reintentos = Objects.isNull(notificacion.getReintentos()) ? 0 : notificacion.getReintentos();
			reintentos += 1;
			notificacion.setReintentos(reintentos);

			// El aviso ha sido enviado
			if (resultado.equals(ResultadoEnvio.OK)) {
				aviso.setEstaNotificado(Boolean.TRUE);
				notificacion.setEstado(EstadoNotificacion.ENVIADA.name());
			} else {

				aviso.setEstaNotificado(Boolean.FALSE);

				if (reintentos < maxNumReintentos) {
					// Si no ha llegado al número de reintentos, estado no enviada
					notificacion.setEstado(EstadoNotificacion.NO_ENVIADA.name());
				} else {
					// Si ha llegado al número de reintentos, estado a fallido
					notificacion.setEstado(EstadoNotificacion.FALLIDA.name());
				}
			}

			String result = (resultado) ? ResultadoEnvio.OK.name() : ResultadoEnvio.FALLO.name();
			notificacion.setResultado(result);
			notificacionRepository.save(notificacion);
			avisoRepository.save(aviso);
			
			RegistroNotificacion registroNotificacion = RegistroNotificacion.builder()
					.notificacion(notificacion)
					.cliente(cliente)
					.resultado(notificacion.getResultado())
					.fechaHoraNotificacion(notificacion.getFechaHoraEnvio())
					.build();

			registroNotificacionRepository.save(registroNotificacion);
		} catch (Exception e) {
			log.error("Error: {}", e.getMessage());
			throw e;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<NotificacionDTO> getNotificaciones(String estado) {
		try {
			List<Notificacion> list = notificacionRepository.findByEstado(estado);
			return new ArrayList<>(notificacionAssembler.assemble(list));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public NotificacionDTO findById(Long id) {
		try {
			Optional<Notificacion> oNotificacion = notificacionRepository.findById(id);
			return oNotificacion.isPresent() ? notificacionAssembler.assemble(oNotificacion.get()) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}
}
