package net.bounceme.chronos.comunicacion.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
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
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
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

	private static final Logger log = Logger.getLogger(DireccionesClienteServiceImpl.class);

	@Autowired
	@Qualifier(AppConfig.DIRECCIONES_CLIENTE_REPOSITORY)
	private DaoPersistence<DireccionCliente> direccionesClienteRepository;

	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public MedioComunicacionCliente nuevo(Long idCliente, String direccion, String numero, String escalera,
			Integer piso, String puerta, String localidad, String provincia, String codigoPostal)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DireccionCliente get(Long idCliente, Long idDireccion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(Long idCliente, Long idDireccion, String direccion, String numero, String escalera,
			Integer piso, String puerta, String localidad, String provincia, String codigoPostal)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long idCliente, Long idDireccion) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DireccionCliente> listar(Long idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
