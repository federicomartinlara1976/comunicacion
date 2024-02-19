package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.data.dao.AvisoRepository;
import net.bounceme.chronos.comunicacion.data.dao.ClienteRepository;
import net.bounceme.chronos.comunicacion.data.dao.NotificacionRepository;
import net.bounceme.chronos.comunicacion.data.model.Aviso;
import net.bounceme.chronos.comunicacion.data.model.Cliente;
import net.bounceme.chronos.comunicacion.data.model.Notificacion;
import net.bounceme.chronos.comunicacion.dto.AvisoDTO;
import net.bounceme.chronos.comunicacion.services.AvisosService;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona los avisos
 * 
 * @author frederik
 *
 */
@Service
@Slf4j
public class AvisosServiceImpl implements AvisosService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AvisoRepository avisosRepository;

	@Autowired
	private NotificacionRepository notificacionesRepository;
	
	@Autowired
	@Qualifier("avisoAssembler")
	private BidirectionalAssembler<Aviso, AvisoDTO> avisoAssembler;

	@Override
	@Transactional
	@SneakyThrows
	public AvisoDTO save(Long id, AvisoDTO avisoDTO) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(id);
			
			Aviso aviso = avisoAssembler.reverseAssemble(avisoDTO);
			aviso.setCliente(oCliente.isPresent() ? oCliente.get() : null);
			aviso = avisosRepository.save(aviso);
			
			return avisoAssembler.assemble(aviso);
		} catch (AssembleException e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void anularAviso(AvisoDTO avisoDTO) {
		try {
			Optional<Aviso> oAviso = avisosRepository.findById(avisoDTO.getId());

			if (oAviso.isPresent()) {
				Aviso aviso = oAviso.get();
				
				// Si está notificado este aviso, no lo borra
				if (!aviso.getEstaNotificado()) {
					for (Notificacion notificacion : aviso.getNotificaciones()) {
						notificacionesRepository.deleteById(notificacion.getId());
					}

					avisosRepository.deleteById(aviso.getId());
				}
			}
		} catch (Exception e) {
			log.error("Error: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AvisoDTO findById(Long id) {
		try {
			Optional<Aviso> oAviso = avisosRepository.findById(id);
			return oAviso.isPresent() ? avisoAssembler.assemble(oAviso.get()) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<AvisoDTO> list() {
		try {
			List<Aviso> avisos = avisosRepository.findAll();
			return new ArrayList<>(avisoAssembler.assemble(avisos));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		avisosRepository.deleteById(id);
	}
}
