package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.services.AvisosService;

/**
 * Implementación del servicio que gestiona los avisos
 * 
 * @author frederik
 *
 */
@Service(AvisosService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AvisosServiceImpl implements AvisosService {
	private static final Logger log = LoggerFactory.getLogger(AvisosServiceImpl.class);

	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@Autowired
	@Qualifier(AppConfig.DIRECCIONES_CLIENTE_REPOSITORY)
	private DaoPersistence<DireccionCliente> direccionClienteRepository;
	
	@Autowired
	@Qualifier(AppConfig.AVISOS_REPOSITORY)
	private DaoPersistence<Aviso> avisosRepository;

	@Autowired
	@Qualifier(AppConfig.NOTIFICACIONES_REPOSITORY)
	private DaoPersistence<Notificacion> notificacionesRepository;
	
	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.AvisosService#nuevoAviso(java.
	 * lang.Long, java.util.Date, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Aviso nuevoAviso(Long idCliente, Long idDireccion, Date fechaInicioObra, String mensaje) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);
			DireccionCliente direccionCliente = direccionClienteRepository.getObject(idDireccion);
			
			Aviso aviso = new Aviso();
			aviso.setCliente(cliente);
			aviso.setDireccionCliente(direccionCliente);
			aviso.setFechaInicioObra(fechaInicioObra);
			aviso.setMensaje(mensaje);
			aviso.setEstaNotificado(Boolean.FALSE);

			return avisosRepository.saveObject(aviso);
		} catch (Exception e) {
			log.error("ERROR: ", e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.AvisosService#anularAviso(java
	 * .lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void anularAviso(Long idAviso) throws ServiceException {
		try {
			Aviso aviso = avisosRepository.getObject(idAviso);

			// Si está notificado este aviso, no lo borra
			if (!aviso.getEstaNotificado()) {
				for (Notificacion notificacion : aviso.getNotificaciones()) {
					notificacionesRepository.removeObject(notificacion.getId());
				}

				avisosRepository.removeObject(idAviso);
			}
		} catch (Exception e) {
			log.error("ERROR: ", e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.AvisosService#get(java.lang.
	 * Long)
	 */
	@Override
	public Aviso get(Long id) {
		return avisosRepository.getObject(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aviso> listar() {
		return new ArrayList<>(daoQueries.executeNamedQuery("avisos", Boolean.TRUE));
	}
}
