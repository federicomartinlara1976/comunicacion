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
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.repository.ClienteRepository;
import net.bounceme.chronos.comunicacion.repository.DireccionClienteRepository;
import net.bounceme.chronos.comunicacion.services.DireccionesClienteService;
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
public class DireccionesClienteServiceImpl implements DireccionesClienteService {

	@Autowired
	private DireccionClienteRepository direccionClienteRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	@Qualifier("direccionClienteAssembler")
	private BidirectionalAssembler<DireccionCliente, DireccionClienteDTO> direccionClienteAssembler;
	
	@Override
	@Transactional
	@SneakyThrows
	public DireccionClienteDTO save(Long id, DireccionClienteDTO direccionClienteDTO) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(id);
			
			DireccionCliente direccionCliente = direccionClienteAssembler.reverseAssemble(direccionClienteDTO);
			direccionCliente.setCliente(oCliente.isPresent() ? oCliente.get() : null);
			direccionCliente = direccionClienteRepository.save(direccionCliente);
			
			return direccionClienteAssembler.assemble(direccionCliente);
		} catch (AssembleException e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	@SneakyThrows
	public DireccionClienteDTO save(DireccionClienteDTO direccionClienteDTO) {
		try {
			DireccionCliente direccionCliente = direccionClienteAssembler.reverseAssemble(direccionClienteDTO);
			direccionCliente = direccionClienteRepository.save(direccionCliente);
			
			return direccionClienteAssembler.assemble(direccionCliente);
		} catch (AssembleException e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public DireccionClienteDTO findById(Long id) {
		try {
			Optional<DireccionCliente> oDireccionCliente = direccionClienteRepository.findById(id);
			return oDireccionCliente.isPresent() ? direccionClienteAssembler.assemble(oDireccionCliente.get()) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		direccionClienteRepository.deleteById(id);
	}

	@Override
	public List<DireccionClienteDTO> list(Long id) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(id);
			if (oCliente.isPresent()) {
				List<DireccionCliente> direcciones = direccionClienteRepository.findByCliente(oCliente.get());
				return new ArrayList<>(direccionClienteAssembler.assemble(direcciones));
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
