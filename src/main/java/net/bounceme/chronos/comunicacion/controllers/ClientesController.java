package net.bounceme.chronos.comunicacion.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.services.ClientesService;

/**
 * Controlador para la gestión de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/clientes")
public class ClientesController {

	private static final Logger log = Logger.getLogger(ClientesController.class);
	
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
	@RequestMapping(method = RequestMethod.GET)
	public List<Cliente> listAll() {
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
	@RequestMapping(
			value="/new", 
			method = RequestMethod.POST,
			consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente nuevo(
			@RequestBody Cliente cliente) throws ControllerException {
		try {
			return clientesService.nuevo(cliente.getNombre(), cliente.getApellidos(), cliente.getDni());
		} catch (ServiceException exception) {
			log.error(exception);
			throw new ControllerException(exception);
		}
	}
	
	/**
	 * Obtiene un cliente
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> get(@PathVariable Long id) {
		Cliente cliente = clientesService.get(id);
		HttpStatus status = cliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Cliente>(cliente, status);
	}
	
	/**
	 * Actualiza un cliente.
	 * 
	 * @param id identificador del cliente
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value="/{id}/update", 
			method = RequestMethod.PUT,
			consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void actualizar(
			@PathVariable Long id,
			@RequestBody Cliente cliente) throws ControllerException {
		try {
			clientesService.actualizar(id, cliente.getNombre(), cliente.getApellidos(), cliente.getDni());
		} catch (ServiceException exception) {
			log.error(exception);
			throw new ControllerException(exception);
		}
	}
	
	/**
	 * Borra un cliente.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value="/{id}/delete", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void borrar(@PathVariable Long id) throws ControllerException {
		try {
			clientesService.borrar(id);
		} catch (ServiceException exception) {
			log.error(exception);
			throw new ControllerException(exception);
		}
	}
}
