package net.bounceme.chronos.comunicacion.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
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
import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Interfaz del servicio de gestión de clientes
 * 
 * @author frederik
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClientesService {
	private static final String ERROR = "ERROR: ";

    private static final Logger log = LoggerFactory.getLogger(ClientesService.class);

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
	
	@Autowired
	@Qualifier("clienteAssembler")
	private Assembler<Cliente, ClienteDTO> clienteAssembler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#nuevo(java.
	 * lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ClienteDTO nuevo(@NotBlank String nombre, @NotBlank String apellidos, String dni) throws ServiceException {
		try {
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre);
			cliente.setApellidos(apellidos);
			cliente.setDni(dni);

			return clienteAssembler.assemble(clientesRepository.saveObject(cliente));
		} catch (Exception e) {
			log.error(ERROR, e);
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
	public ClienteDTO get(Long id) {
		try {
			return clienteAssembler.assemble(clientesRepository.getObject(id));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#actualizar(
	 * java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
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
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#borrar(java.
	 * lang.Long)
	 */
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

			clientesRepository.removeObject(cliente);
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#listar()
	 */
	@SuppressWarnings("unchecked")
	public List<ClienteDTO> listar() {
		try {
			return new ArrayList<>(clienteAssembler.assemble(daoQueries.executeNamedQuery("clientes", Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#buscarPorNombre(java.lang.String)
	 */
	public List<ClienteDTO> buscarPorNombre(String nombre) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("nombre", "%" + nombre + "%");
		
		return metodoBuscar("buscarClientesPorNombre", parameters);
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#buscarPorNombreYApellidos(java.lang.String, java.lang.String)
	 */
	public List<ClienteDTO> buscarPorNombreYApellidos(String nombre, String apellidos) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("nombre", "%" + nombre + "%");
		parameters.put("apellidos", "%" + apellidos + "%");
		
		return metodoBuscar("buscarClientesPorNombreYApellidos", parameters);
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.ClientesService#buscarPorDNI(java.lang.String)
	 */
	public List<ClienteDTO> buscarPorDNI(String dni) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dni", dni);
		
		return metodoBuscar("buscarClientesPorDNI", parameters);
	}

	public List<ClienteDTO> buscarPorApellidos(String apellidos) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("apellidos", "%" + apellidos + "%");
		
		return metodoBuscar("buscarClientesPorApellidos", parameters);
	}

	@SuppressWarnings("unchecked")
	private List<ClienteDTO> metodoBuscar(String consulta, Map<String, Object> parameters) {
		try {
			return new ArrayList<>(clienteAssembler.assemble(daoQueries.executeNamedQuery(consulta, parameters, Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}
	
}
