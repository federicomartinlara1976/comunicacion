package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
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
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.services.DireccionesClienteService;

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
	public DireccionCliente nuevo(Long idCliente, String direccion, String numero, String escalera,
			Integer piso, String puerta, String localidad, String provincia, String codigoPostal)
			throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);
			
			boolean isNew = true;
			DireccionCliente direccionCliente = obtenerDireccion(cliente, null, isNew);
			fillDireccion(direccionCliente, direccion, numero, escalera, piso, puerta, localidad, provincia, codigoPostal);
			
			log.debug("Direccion creada correctamente");
			return direccionesClienteRepository.saveObject(direccionCliente);
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
	public DireccionCliente get(Long idDireccion) {
		return getDireccionCliente(idDireccion);
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
	public void actualizar(Long idCliente, Long idDireccion, String direccion, String numero, String escalera,
			Integer piso, String puerta, String localidad, String provincia, String codigoPostal)
			throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);

			DireccionCliente direccionCliente = obtenerDireccion(cliente, idDireccion, false);
			fillDireccion(direccionCliente, direccion, numero, escalera, piso, puerta, localidad, provincia, codigoPostal);

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
	public List<DireccionCliente> listar(Long idCliente) {
		Cliente cliente = clientesRepository.getObject(idCliente);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cliente", cliente);
		return new ArrayList<>(
				daoQueries.executeNamedQuery("direccionesCliente", parameters, Boolean.TRUE));
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
	private void fillDireccion(DireccionCliente direccionCliente, String direccion, String numero, String escalera,
			Integer piso, String puerta, String localidad, String provincia, String codigoPostal) {
		direccionCliente.setDireccion(direccion);
		direccionCliente.setNumero(numero);
		direccionCliente.setEscalera(escalera);
		direccionCliente.setPiso(piso);
		direccionCliente.setPuerta(puerta);
		direccionCliente.setLocalidad(localidad);
		direccionCliente.setProvincia(provincia);
		direccionCliente.setCodigoPostal(codigoPostal);
	}
}
