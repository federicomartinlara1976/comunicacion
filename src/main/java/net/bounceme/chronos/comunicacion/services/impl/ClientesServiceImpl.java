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
import net.bounceme.chronos.comunicacion.data.dao.ClienteRepository;
import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.services.ClientesService;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

/**
 * Implementación del servicio que gestiona los clientes
 * 
 * @author frederik
 *
 */
@Service
@Slf4j
public class ClientesServiceImpl implements ClientesService {
	private static final String LIKE_FORMAT = "%%s%";

    @Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	@Qualifier("clienteAssembler")
	private BidirectionalAssembler<Cliente, ClienteDTO> clienteAssembler;

	@Override
	@Transactional
	@SneakyThrows
	public ClienteDTO save(ClienteDTO clienteDTO) {
		try {
			Cliente cliente = clienteAssembler.reverseAssemble(clienteDTO);
			clienteRepository.save(cliente);
			return clienteAssembler.assemble(cliente);
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		try {
			Optional<Cliente> oCliente = clienteRepository.findById(id);
			return oCliente.isPresent() ? clienteAssembler.assemble(oCliente.get()) : null;
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> list() {
		try {
			List<Cliente> clientes = clienteRepository.findAll();
			return new ArrayList<>(clienteAssembler.assemble(clientes));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> findByNombre(String nombre) {
		try {
			String lNombre = String.format(LIKE_FORMAT, nombre);
			List<Cliente> clientes = clienteRepository.findByNombre(lNombre);
			
			return new ArrayList<>(clienteAssembler.assemble(clientes));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> findByApellidos(String apellidos) {
		try {
			String lApellidos = String.format(LIKE_FORMAT, apellidos);
			List<Cliente> clientes = clienteRepository.findByApellidos(lApellidos);
			
			return new ArrayList<>(clienteAssembler.assemble(clientes));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDTO> findByNombreAndApellidos(String nombre, String apellidos) {
		try {
			String lNombre = String.format(LIKE_FORMAT, nombre);
			String lApellidos = String.format(LIKE_FORMAT, apellidos);
			List<Cliente> clientes = clienteRepository.findByNombreAndApellidos(lNombre, lApellidos);
			
			return new ArrayList<>(clienteAssembler.assemble(clientes));
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteDTO findByDNI(String dni) {
		try {
			Cliente cliente = clienteRepository.findByDni(dni);
			return clienteAssembler.assemble(cliente);
		} catch (AssembleException e) {
			log.error("Error: {}", e.getMessage());
			return null;
		}
	}
}
