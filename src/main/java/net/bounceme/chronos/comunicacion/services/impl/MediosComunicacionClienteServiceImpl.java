package net.bounceme.chronos.comunicacion.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.repository.ClienteRepository;
import net.bounceme.chronos.comunicacion.repository.MedioComunicacionClienteRepository;
import net.bounceme.chronos.comunicacion.services.MediosComunicacionClienteService;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona los medios de comunicación de los
 * clientes
 * 
 * @author frederik
 *
 */
@Service
@Slf4j
public class MediosComunicacionClienteServiceImpl implements MediosComunicacionClienteService {

	@Autowired
	private MedioComunicacionClienteRepository medioComunicacionClienteRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	@Qualifier("medioComunicacionClienteAssembler")
	private BidirectionalAssembler<MedioComunicacionCliente, MedioComunicacionClienteDTO> medioComunicacionClienteAssembler;

	@Override
	@Transactional
	@SneakyThrows
	public MedioComunicacionClienteDTO save(Long id, MedioComunicacionClienteDTO medioComunicacionClienteDTO) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(id);
			
			MedioComunicacionCliente medioComunicacionCliente = medioComunicacionClienteAssembler.reverseAssemble(medioComunicacionClienteDTO);
			medioComunicacionCliente.setCliente(oCliente.isPresent() ? oCliente.get() : null);
			medioComunicacionClienteRepository.save(medioComunicacionCliente);
			return medioComunicacionClienteAssembler.assemble(medioComunicacionCliente);
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional
	@SneakyThrows
	public MedioComunicacionClienteDTO save(MedioComunicacionClienteDTO medioComunicacionClienteDTO) {
		try {
			MedioComunicacionCliente medioComunicacionCliente = medioComunicacionClienteAssembler.reverseAssemble(medioComunicacionClienteDTO);
			medioComunicacionCliente = medioComunicacionClienteRepository.save(medioComunicacionCliente);
			
			return medioComunicacionClienteAssembler.assemble(medioComunicacionCliente);
		} catch (AssembleException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MedioComunicacionClienteDTO findById(Long id) {
		try {
			Optional<MedioComunicacionCliente> oMedioComunicacionCliente = medioComunicacionClienteRepository.findById(id);
			return oMedioComunicacionCliente.isPresent() ? medioComunicacionClienteAssembler.assemble(oMedioComunicacionCliente.get()) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		medioComunicacionClienteRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MedioComunicacionClienteDTO> list(Long id) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(id);
			if (oCliente.isPresent()) {
				List<MedioComunicacionCliente> medios = medioComunicacionClienteRepository.findByCliente(oCliente.get());
				return new ArrayList<>(medioComunicacionClienteAssembler.assemble(medios));
			}
			else {
				return Collections.emptyList();
			}
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MedioComunicacionClienteDTO findByClienteAndTipo(Long idCliente, String tipo) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(idCliente);
			if (oCliente.isPresent()) {
				MedioComunicacionCliente medioComunicacionCliente = medioComunicacionClienteRepository.findByClienteAndTipo(oCliente.get(), tipo);
				return !Objects.isNull(medioComunicacionCliente) ? medioComunicacionClienteAssembler.assemble(medioComunicacionCliente) : null;
			}
			else {
				return null;
			}
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}
}
