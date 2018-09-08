package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.data.dao.DaoPersistence;
import net.bounceme.chronos.comunicacion.data.dao.DaoQueries;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.services.RegistroNotificacionesService;

/**
 * Implementación del servicio que gestiona las notificaciones
 * 
 * @author frederik
 *
 */
@Service(RegistroNotificacionesService.NAME)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RegistroNotificacionesServiceImpl implements RegistroNotificacionesService {
	
	@Autowired
	@Qualifier(DaoQueries.NAME)
	private DaoQueries daoQueries;
	
	@Autowired
	@Qualifier(AppConfig.CLIENTE_REPOSITORY)
	private DaoPersistence<Cliente> clientesRepository;

	@SuppressWarnings("unchecked")
	@Override
	public List<RegistroNotificacion> searchByDates(Date from, Date to) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("from", from);
		parameters.put("to", to);
		
		return new ArrayList<RegistroNotificacion>(daoQueries.executeNamedQuery("registrosNotificacionesPorFecha", parameters, Boolean.TRUE));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegistroNotificacion> searchByClient(Long idClient) {
		Cliente cliente = clientesRepository.getObject(idClient);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cliente", cliente);
		
		return new ArrayList<RegistroNotificacion>(daoQueries.executeNamedQuery("registrosNotificacionesPorCliente", parameters, Boolean.TRUE));
	}

	
}
