package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import net.bounceme.chronos.comunicacion.dto.AvisoDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.services.AvisosService;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona los avisos
 * 
 * @author frederik
 *
 */
@Service(AvisosService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AvisosServiceImpl implements AvisosService {
	private static final String ERROR = "ERROR: ";

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
	
	@Autowired
	@Qualifier("avisoAssembler")
	private Assembler<Aviso, AvisoDTO> avisoAssembler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.AvisosService#nuevoAviso(java.
	 * lang.Long, java.util.Date, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AvisoDTO nuevoAviso(Long idCliente, Long idDireccion, Date fechaInicioObra, String mensaje) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);
			DireccionCliente direccionCliente = direccionClienteRepository.getObject(idDireccion);
			
			Aviso aviso = new Aviso();
			aviso.setCliente(cliente);
			aviso.setDireccionCliente(direccionCliente);
			aviso.setFechaInicioObra(fechaInicioObra);
			aviso.setMensaje(mensaje);
			aviso.setEstaNotificado(Boolean.FALSE);

			return avisoAssembler.assemble(avisosRepository.saveObject(aviso));
		} catch (Exception e) {
			log.error(ERROR, e);
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
			log.error(ERROR, e);
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
	public AvisoDTO get(Long id) {
		try {
			return avisoAssembler.assemble(avisosRepository.getObject(id));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return new AvisoDTO();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AvisoDTO> listar() {
		try {
			return new ArrayList<>(avisoAssembler.assemble(daoQueries.executeNamedQuery("avisos", Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}
}
