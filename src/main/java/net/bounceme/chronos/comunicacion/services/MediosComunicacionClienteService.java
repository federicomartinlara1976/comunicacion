package net.bounceme.chronos.comunicacion.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MediosComunicacionClienteService {

	private static final String ERROR = "ERROR: ";

    private static final Logger log = LoggerFactory.getLogger(MediosComunicacionClienteService.class);

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
	
	@Autowired
	@Qualifier("medioComunicacionClienteAssembler")
	private Assembler<MedioComunicacionCliente, MedioComunicacionClienteDTO> medioComunicacionClienteAssembler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#nuevo(java.lang.Long, java.lang.Long,
	 * java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public MedioComunicacionClienteDTO nuevo(Long idCliente, Long idTipo, String valor) throws ServiceException {
		try {
			MedioComunicacionCliente medioComunicacion = new MedioComunicacionCliente();

			medioComunicacion.setCliente(clientesRepository.getObject(idCliente));
			medioComunicacion.setTipoComunicacion(tiposComunicacionRepository.getObject(idTipo));
			medioComunicacion.setValor(valor);

			return medioComunicacionClienteAssembler.assemble(mediosComunicacionClienteRepository.saveObject(medioComunicacion));
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#get(java.lang.Long, java.lang.Long)
	 */
	public MedioComunicacionClienteDTO get(Long idCliente, Long idTipo) {
		Cliente cliente = clientesRepository.getObject(idCliente);
		TipoComunicacion tipo = tiposComunicacionRepository.getObject(idTipo);

		try {
			return medioComunicacionClienteAssembler.assemble(getMedioComunicacion(cliente, tipo));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#actualizar(java.lang.Long, java.lang.Long,
	 * java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void actualizar(Long idCliente, Long idTipo, String valor) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);
			TipoComunicacion tipo = tiposComunicacionRepository.getObject(idTipo);

			MedioComunicacionCliente medio = getMedioComunicacion(cliente, tipo);

			if (!Objects.isNull(medio) && StringUtils.isNotBlank(valor)) {
				medio.setValor(valor);
			}

			mediosComunicacionClienteRepository.updateObject(medio);
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#borrar(java.lang.Long, java.lang.Long)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void borrar(Long idCliente, Long idTipo) throws ServiceException {
		try {
			Cliente cliente = clientesRepository.getObject(idCliente);
			TipoComunicacion tipo = tiposComunicacionRepository.getObject(idTipo);
			
			MedioComunicacionCliente medio = getMedioComunicacion(cliente, tipo);

			mediosComunicacionClienteRepository.removeObject(medio);
		} catch (Exception e) {
			log.error(ERROR, e);
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.bounceme.chronos.comunicacion.services.
	 * MediosComunicacionClienteService#listar(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<MedioComunicacionClienteDTO> listar(Long idCliente) {
		Cliente cliente = clientesRepository.getObject(idCliente);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cliente", cliente);
		try {
			return new ArrayList<>(medioComunicacionClienteAssembler.assemble(
					daoQueries.executeNamedQuery("mediosComunicacionCliente", parameters, Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error(ERROR, e);
			return Collections.emptyList();
		}
	}
	
	/**
	 * @param cliente
	 * @param tipo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MedioComunicacionCliente getMedioComunicacion(Cliente cliente, TipoComunicacion tipo) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cliente", cliente);
		parameters.put("tipo", tipo);
		Optional<MedioComunicacionCliente> oResult = daoQueries.executeScalarNamedQuery("medioComunicacionCliente", parameters,
				Boolean.TRUE);
		
		return (oResult.isPresent()) ? oResult.get() : null;
	}
}