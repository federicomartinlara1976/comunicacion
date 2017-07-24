package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.ComunicacionApplication;
import net.bounceme.chronos.comunicacion.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.exceptions.DataException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.services.ClientesService;
import net.bounceme.chronos.comunicacion.utils.Finalizer;

/**
 * Implementación del servicio que gestiona los clientes
 * 
 * @author frederik
 *
 */
@Service(ClientesService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClientesServiceImpl extends Finalizer implements ClientesService {

	private static final Logger log = Logger.getLogger(ClientesServiceImpl.class);

	@Autowired
	@Qualifier(ComunicacionApplication.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@Autowired
	@Qualifier(ComunicacionApplication.MEDIOS_COMUNICACION_CLIENTE_REPOSITORY)
	private DaoPersistence<MedioComunicacionCliente> mediosComunicacionClienteRepository;

	@Autowired
	@Qualifier(ComunicacionApplication.AVISOS_REPOSITORY)
	private DaoPersistence<Aviso> avisosRepository;
	
	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	/*
	 * (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#nuevo(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Cliente nuevo(String nombre, String apellidos, String dni) throws ServiceException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre);
			cliente.setApellidos(apellidos);
			cliente.setDni(dni);

			return clientesRepository.saveObject(cliente);
		}
		catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#get(java.lang.Long)
	 */
	@Override
	public Cliente get(Long id) {
		return clientesRepository.getObject(id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#actualizar(java.lang.Long, java.lang.String,
	 * java.lang.String, java.lang.String)
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
		}
		catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#borrar(java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long id) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(id);

			// Si tuviera medios de comunicación, los disocia
			CollectionUtils.forAllDo(cliente.getMediosComunicacion(), new Closure() {
				public void execute(Object o) {
					try {
						MedioComunicacionCliente medio = (MedioComunicacionCliente) o;
						medio = mediosComunicacionClienteRepository.getObject(medio.getId());
						medio.setCliente(null);
						mediosComunicacionClienteRepository.updateObject(medio);
					} catch (DataException exception) {
						log.error(exception);
					}
				}
			});

			// Si tuviera avisos no notificados, los borra antes
			CollectionUtils.forAllDo(cliente.getAvisos(), new Closure() {
				public void execute(Object o) {
					try {
						Aviso aviso = (Aviso) o;
						aviso.setCliente(null);
						avisosRepository.updateObject(aviso);
					} catch (DataException exception) {
						log.error(exception);
					}
				}
			});
			
			clientesRepository.removeObject(id);
		} catch (DataException exception) {
			log.error(exception);
			throw new ServiceException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#listar()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listar() {
		return new ArrayList<Cliente>(daoQueries.executeNamedQuery("clientes", Boolean.TRUE));
	}

}
