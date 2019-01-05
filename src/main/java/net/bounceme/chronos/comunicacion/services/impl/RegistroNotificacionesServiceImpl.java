package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import net.bounceme.chronos.comunicacion.dto.RegistroNotificacionDTO;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.services.RegistroNotificacionesService;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona las notificaciones
 * 
 * @author frederik
 *
 */
@Service(RegistroNotificacionesService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RegistroNotificacionesServiceImpl implements RegistroNotificacionesService {
	
	private static final Logger log = LoggerFactory.getLogger(RegistroNotificacionesServiceImpl.class);
	
	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;
	
	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;
	
	@Autowired
	@Qualifier("registroNotificacionAssembler")
	private Assembler<RegistroNotificacion, RegistroNotificacionDTO> registroNotificacionAssembler;

	@Override
	public List<RegistroNotificacionDTO> searchByDates(Date from, Date to) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("from", from);
		parameters.put("to", to);
		
		return metodoConsulta("registrosNotificacionesPorFecha", parameters);
	}

	@Override
	public List<RegistroNotificacionDTO> searchByClient(Long idClient) {
		Cliente cliente = clientesRepository.getObject(idClient);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("cliente", cliente);
		
		return metodoConsulta("registrosNotificacionesPorCliente", parameters);
	}

	@SuppressWarnings("unchecked")
	private List<RegistroNotificacionDTO> metodoConsulta(String consulta, Map<String, Object> parameters) {
		try {
			return new ArrayList<>(registroNotificacionAssembler.assemble(
					daoQueries.executeNamedQuery(consulta, parameters, Boolean.TRUE)));
		} catch (AssembleException e) {
			log.error("ERROR: ", e);
			return Collections.emptyList();
		}
	}
}
