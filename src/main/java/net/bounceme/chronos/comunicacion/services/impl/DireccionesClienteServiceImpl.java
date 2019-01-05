package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.services.DireccionesClienteService;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona los medios de comunicación de los
 * clientes
 * 
 * @author frederik
 *
 */
@Service(DireccionesClienteService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DireccionesClienteServiceImpl implements DireccionesClienteService {

	private static final String ERROR = "ERROR: ";

    private static final Logger log = LoggerFactory.getLogger(DireccionesClienteServiceImpl.class);

	@Autowired
	@Qualifier(AppConfig.DIRECCIONES_CLIENTE_REPOSITORY)
	private DaoPersistence<DireccionCliente> direccionesClienteRepository;

	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;
	
	@Autowired
	@Qualifier("direccionClienteAssembler")
	private Assembler<DireccionCliente, DireccionClienteDTO> direccionClienteAssembler;

	/**
	 * @param idCliente
	 * @param direccion
	 * @param numero
	 * @param escalera
	 * @param piso
	 * @param puerta
	 * @param localidad
	 * @param provincia
	 * @param codigoPostal
	 * @return
	 * @throws ServiceException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DireccionClienteDTO nuevo(Long idCliente, DireccionClienteDTO direccionClienteDTO)
			throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);
			
			boolean isNew = true;
			DireccionCliente direccionCliente = obtenerDireccion(cliente, null, isNew);
			fillDireccion(direccionCliente, direccionClienteDTO);
			
			return direccionClienteAssembler.assemble(direccionesClienteRepository.saveObject(direccionCliente));
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @return
	 */
	@Override
	public DireccionClienteDTO get(Long idDireccion) {
		try {
			return direccionClienteAssembler.assemble(getDireccionCliente(idDireccion));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return null;
		}
	}

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @param direccion
	 * @param numero
	 * @param escalera
	 * @param piso
	 * @param puerta
	 * @param localidad
	 * @param provincia
	 * @param codigoPostal
	 * @throws ServiceException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(DireccionClienteDTO direccionClienteDTO)
			throws ServiceException {
		try {

			DireccionCliente direccionCliente = obtenerDireccion(null, direccionClienteDTO.getId(), false);
			fillDireccion(direccionCliente, direccionClienteDTO);

			direccionesClienteRepository.updateObject(direccionCliente);
			log.debug("Direccion modificada correctamente");
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @throws ServiceException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long idDireccion) throws ServiceException {
		try {
			DireccionCliente direccion = getDireccionCliente(idDireccion);

			direccionesClienteRepository.removeObject(direccion);
			log.debug("Direccion borrada correctamente");
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/**
	 * @param idCliente
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DireccionClienteDTO> listar(Long idCliente) {
		Cliente cliente = clientesRepository.getObject(idCliente);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cliente", cliente);
		
		try {
			return new ArrayList<>(direccionClienteAssembler.assemble(
					daoQueries.executeNamedQuery("direccionesCliente", parameters, Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}

	/**
	 * @param cliente
	 * @param idDireccion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private DireccionCliente getDireccionCliente(Long idDireccion) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("idDireccion", idDireccion);
		Optional<DireccionCliente> oResult = daoQueries.executeScalarNamedQuery("direccionCliente", parameters,
				Boolean.TRUE);
		
		return (oResult.isPresent()) ? oResult.get() : null;
	}
	
	/**
	 * @param cliente
	 * @param idDireccion
	 * @param isNew
	 * @return
	 */
	private DireccionCliente obtenerDireccion(Cliente cliente, Long idDireccion, boolean isNew) {
		DireccionCliente direccionCliente;
		
		if (isNew) {
			direccionCliente = new DireccionCliente();
			direccionCliente.setCliente(cliente);
		}
		else {
			direccionCliente = getDireccionCliente(idDireccion);
		}
		
		return direccionCliente;
	}
	
	/**
	 * @param direccionCliente
	 * @param direccion
	 * @param numero
	 * @param escalera
	 * @param piso
	 * @param puerta
	 * @param localidad
	 * @param provincia
	 * @param codigoPostal
	 */
	private void fillDireccion(DireccionCliente direccionCliente, DireccionClienteDTO direccionClienteDTO) {
		direccionCliente.setDireccion(direccionClienteDTO.getDireccion());
		direccionCliente.setNumero(direccionClienteDTO.getNumero());
		direccionCliente.setEscalera(direccionClienteDTO.getEscalera());
		direccionCliente.setPiso(direccionClienteDTO.getPiso());
		direccionCliente.setPuerta(direccionClienteDTO.getPuerta());
		direccionCliente.setLocalidad(direccionClienteDTO.getLocalidad());
		direccionCliente.setProvincia(direccionClienteDTO.getProvincia());
		direccionCliente.setCodigoPostal(direccionClienteDTO.getCodigoPostal());
	}
}
