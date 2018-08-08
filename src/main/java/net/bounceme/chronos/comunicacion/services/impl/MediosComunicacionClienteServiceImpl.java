package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionClienteId;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.comunicacion.services.MediosComunicacionClienteService;

/**
 * Implementación del servicio que gestiona los medios de comunicación de los
 * clientes
 * 
 * @author frederik
 *
 */
@Service(MediosComunicacionClienteService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MediosComunicacionClienteServiceImpl implements MediosComunicacionClienteService {

	@Autowired
	@Qualifier(AppConfig.MEDIOS_COMUNICACION_CLIENTE_REPOSITORY)
	private DaoPersistence<MedioComunicacionCliente> mediosComunicacionClienteRepository;

	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@Autowired
	@Qualifier(AppConfig.TIPOS_COMUNICACION_REPOSITORY)
	private DaoPersistence<TipoComunicacion> tiposComunicacionRepository;

	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#nuevo(java.lang.Long, java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public MedioComunicacionCliente nuevo(Long idCliente, Long idTipo, String valor) {
		MedioComunicacionClienteId id = getIdentificador(idCliente, idTipo);

		MedioComunicacionCliente medioComunicacion = new MedioComunicacionCliente();

		medioComunicacion.setId(id);
		medioComunicacion.setCliente(id.getCliente());
		medioComunicacion.setValor(valor);

		return mediosComunicacionClienteRepository.saveObject(medioComunicacion);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#get(java.lang.Long, java.lang.Long)
	 */
	@Override
	public MedioComunicacionCliente get(Long idCliente, Long idTipo) {
		MedioComunicacionClienteId id = getIdentificador(idCliente, idTipo);
		return mediosComunicacionClienteRepository.getObject(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#actualizar(java.lang.Long,
	 * java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(Long idCliente, Long idTipo, String valor) {
		MedioComunicacionClienteId id = getIdentificador(idCliente, idTipo);
		MedioComunicacionCliente medio = mediosComunicacionClienteRepository.getObject(id);

		if (StringUtils.isNotBlank(valor)) {
			medio.setValor(valor);
		}

		mediosComunicacionClienteRepository.updateObject(medio);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#borrar(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long idCliente, Long idTipo) {
		MedioComunicacionClienteId id = getIdentificador(idCliente, idTipo);
		mediosComunicacionClienteRepository.removeObject(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#listar(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MedioComunicacionCliente> listar(Long idCliente) {
		Cliente cliente = clientesRepository.getObject(idCliente);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cliente", cliente);
		return new ArrayList<MedioComunicacionCliente>(
				daoQueries.executeNamedQuery("mediosComunicacionCliente", parameters, Boolean.TRUE));
	}

	/**
	 * @param idCliente
	 * @param idTipo
	 * @return
	 */
	private MedioComunicacionClienteId getIdentificador(Long idCliente, Long idTipo) {
		return new MedioComunicacionClienteId(clientesRepository.getObject(idCliente),
				tiposComunicacionRepository.getObject(idTipo));
	}
}
