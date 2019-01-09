package net.bounceme.chronos.comunicacion.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamCliente;
import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.services.ClientesService;

/**
 * Controlador para la gestión de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/api/clientes")
public class ClientesController {
	private static final String ERROR = "ERROR: ";

	Logger log = LoggerFactory.getLogger(ClientesController.class);

	@Autowired
	@Qualifier(ClientesService.NAME)
	private ClientesService clientesService;

	/**
	 * Lista todos los clientes
	 * 
	 * @return listado de clientes
	 * @throws ServiceException
	 */
	@CrossOrigin
	@GetMapping
	public List<ClienteDTO> listAll() {
		return clientesService.listar();
	}
	
	/**
	 * Crea un cliente
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PostMapping(value = "/search", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public List<ClienteDTO> buscar(@RequestBody ParamCliente cliente) {
		List<ClienteDTO> clientes = new ArrayList<>();
		if (!Objects.isNull(cliente.getName()) && !Objects.isNull(cliente.getLastName())) {
			clientes = clientesService.buscarPorNombreYApellidos(cliente.getName(), cliente.getLastName());
		}
		else {
			if (!Objects.isNull(cliente.getName())) {
				clientes = clientesService.buscarPorNombre(cliente.getName());
			}
			
			if (!Objects.isNull(cliente.getLastName())) {
				clientes = clientesService.buscarPorApellidos(cliente.getLastName());
			}
			
			if (!Objects.isNull(cliente.getIdentification())) {
				clientes = clientesService.buscarPorDNI(cliente.getIdentification());
			}
		}
	
		return clientes;
	}

	/**
	 * Crea un cliente
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PostMapping(value = "/new", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO nuevo(@RequestBody ParamCliente cliente) throws ControllerException {
		try {
			return clientesService.nuevo(cliente.getName(), cliente.getLastName(), cliente.getIdentification());
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Obtiene un cliente
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> get(@PathVariable Long id) {
		ClienteDTO cliente = clientesService.get(id);
		HttpStatus status = cliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(cliente, status);
	}

	/**
	 * Actualiza un cliente.
	 * 
	 * @param id        identificador del cliente
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PutMapping(value = "/{id}/update", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void actualizar(@PathVariable Long id, @RequestBody ParamCliente cliente) throws ControllerException {
		try {
			clientesService.actualizar(id, cliente.getName(), cliente.getLastName(), cliente.getIdentification());
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Borra un cliente.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@DeleteMapping(value = "/{id}/delete")
	@ResponseStatus(HttpStatus.OK)
	public void borrar(@PathVariable Long id) throws ControllerException {
		try {
			clientesService.borrar(id);
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}
}
