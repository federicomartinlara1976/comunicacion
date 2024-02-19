package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.data.dao.ClienteRepository;
import net.bounceme.chronos.comunicacion.data.dao.RegistroNotificacionRepository;
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
@Service
@Slf4j
public class RegistroNotificacionesServiceImpl implements RegistroNotificacionesService {
	
	@Autowired
	private ClienteRepository clientesRepository;
	
	@Autowired
	private RegistroNotificacionRepository registroNotificacionRepository; 
	
	@Autowired
	@Qualifier("registroNotificacionAssembler")
	private Assembler<RegistroNotificacion, RegistroNotificacionDTO> registroNotificacionAssembler;

	@Override
	public List<RegistroNotificacionDTO> searchByDates(Date from, Date to) {
		try {
			List<RegistroNotificacion> registros = registroNotificacionRepository.findByDateRange(from, to);
			return new ArrayList<>(registroNotificacionAssembler.assemble(registros));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}	
	}

	@Override
	public List<RegistroNotificacionDTO> searchByClient(Long idClient) {
		try {
			Optional<Cliente> oCliente = clientesRepository.findById(idClient);
			
			if (oCliente.isPresent()) {
				List<RegistroNotificacion> registros = registroNotificacionRepository.findByCliente(oCliente.get());
				return new ArrayList<>(registroNotificacionAssembler.assemble(registros));
			}
			else {
				return Collections.emptyList();
			}
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}
}
