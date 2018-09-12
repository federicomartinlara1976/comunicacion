package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
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
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.services.ClientesService;

/**
 * Implementación del servicio que gestiona los clientes
 * 
 * @author frederik
 *
 */
@Service(ClientesService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClientesServiceImpl implements ClientesService {
	private static final Logger log = Logger.getLogger(ClientesServiceImpl.class);

	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@Autowired
	@Qualifier(AppConfig.MEDIOS_COMUNICACION_CLIENTE_REPOSITORY)
	private DaoPersistence<MedioComunicacionCliente> mediosComunicacionClienteRepository;

	@Autowired
	@Qualifier(AppConfig.AVISOS_REPOSITORY)
	private DaoPersistence<Aviso> avisosRepository;

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#nuevo(java.
	 * lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Cliente nuevo(@NotBlank String nombre, @NotBlank String apellidos, String dni) throws ServiceException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre);
			cliente.setApellidos(apellidos);
			cliente.setDni(dni);

			return clientesRepository.saveObject(cliente);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.bounceme.chronos.comunicacion.services.ClientesService#get(java.lang.
	 * Long)
	 */
	@Override
	public Cliente get(Long id) {
		return clientesRepository.getObject(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#actualizar(
	 * java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(Long id, String nombre, String apellidos, String dni) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(id);

			if (StringUtils.isNotBlank(nombre)) {
				cliente.setNombre(nombre);
			}

			if (StringUtils.isNotBlank(apellidos)) {
				cliente.setApellidos(apellidos);
			}

			if (StringUtils.isNotBlank(dni)) {
				cliente.setDni(dni);
			}

			clientesRepository.updateObject(cliente);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#borrar(java.
	 * lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long id) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(id);

			// Si tuviera medios de comunicación, los disocia
			for (MedioComunicacionCliente medio : cliente.getMediosComunicacion()) {
				medio = mediosComunicacionClienteRepository.getObject(medio.getId());
				medio.setCliente(null);
				mediosComunicacionClienteRepository.updateObject(medio);
			}

			// Si tuviera avisos no notificados, los borra antes
			for (Aviso aviso : cliente.getAvisos()) {
				aviso.setCliente(null);
				avisosRepository.updateObject(aviso);
			}

			clientesRepository.removeObject(id);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#listar()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listar() {
		return new ArrayList<Cliente>(daoQueries.executeNamedQuery("clientes", Boolean.TRUE));
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#buscarPorNombre(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarPorNombre(String nombre) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nombre", "%" + nombre + "%");
		
		return new ArrayList<Cliente>(daoQueries.executeNamedQuery("buscarClientesPorNombre", Boolean.TRUE));
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#buscarPorNombreYApellidos(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarPorNombreYApellidos(String nombre, String apellidos) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nombre", "%" + nombre + "%");
		parameters.put("apellidos", "%" + apellidos + "%");
		
		return new ArrayList<Cliente>(daoQueries.executeNamedQuery("buscarClientesPorNombreYApellidos", Boolean.TRUE));
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#buscarPorDNI(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> buscarPorDNI(String dni) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("dni", dni);
		
		return new ArrayList<Cliente>(daoQueries.executeNamedQuery("buscarClientesPorDNI", Boolean.TRUE));
	}

}
