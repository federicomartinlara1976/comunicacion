package net.bounceme.chronos.comunicacion.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.services.ClientesService;

/**
 * Controlador para la gestión de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClientesController {

	@Autowired
	private ClientesService clientesService;

	@CrossOrigin
	@GetMapping("/")
	public List<ClienteDTO> listAll() {
		return clientesService.list();
	}
	
	@CrossOrigin
	@PostMapping(value = "/search")
	public List<ClienteDTO> buscar(@RequestBody ClienteDTO cliente) {
		List<ClienteDTO> clientes = new ArrayList<>();
		if (!StringUtils.isNotBlank(cliente.getNombre()) && !StringUtils.isNotBlank(cliente.getApellidos())) {
			clientes = clientesService.findByNombreAndApellidos(cliente.getNombre(), cliente.getApellidos());
		}
		else {
			if (!StringUtils.isNotBlank(cliente.getNombre())) {
				clientes = clientesService.findByNombre(cliente.getNombre());
			}
			
			if (!StringUtils.isNotBlank(cliente.getApellidos())) {
				clientes = clientesService.findByApellidos(cliente.getApellidos());
			}
			
			if (!StringUtils.isNotBlank(cliente.getDni())) {
				clientes.add(clientesService.findByDNI(cliente.getDni()));
			}
		}
	
		return clientes;
	}

	@CrossOrigin
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody ClienteDTO cliente) {
		Map<String, Object> response = new HashMap<>();

		cliente = clientesService.save(cliente);

		log.info("Creado: {}", cliente.toString());
		response.put("mensaje", "El cliente ha sido creado con éxito");
		response.put("cliente", cliente);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		ClienteDTO cliente = clientesService.findById(id);

		if (Objects.isNull(cliente)) {
			response.put("mensaje", String.format("El cliente con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ClienteDTO>(cliente, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> actualizar(@Valid @RequestBody ClienteDTO cliente, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		ClienteDTO clienteActual = clientesService.findById(id);
		if (Objects.isNull(clienteActual)) {
			response.put("mensaje", String.format("El cliente con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellidos(cliente.getApellidos());
		clienteActual.setDni(cliente.getDni());

		clienteActual = clientesService.save(clienteActual);

		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		response.put("cliente", clienteActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		clientesService.delete(id);

		response.put("mensaje", "El cliente ha sido borrado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
