package net.bounceme.chronos.comunicacion.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsDireccion;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.services.DireccionesClienteService;

/**
 * Controlador para la gestión de medios de comunicación de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/clientes/direcciones")
public class DireccionesClienteController {
	Logger log = Logger.getLogger(DireccionesClienteController.class);
	
	@Autowired
	@Qualifier(DireccionesClienteService.NAME)
	private DireccionesClienteService direccionesClienteService;

	/**
	 * Lista los medios para comunicarse con un cliente
	 * 
	 * @param idCliente
	 * @return listado de medios
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public List<DireccionCliente> listAll(@RequestParam(value = "idCliente") Long idCliente) {
		return direccionesClienteService.listar(idCliente);
	}

	/**
	 * @param medio parámetros de entrada [idCliente, idTipo, valor]
	 * @return medio creado
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public MedioComunicacionCliente nuevo(@RequestBody ParamsDireccion direccion) throws ControllerException {
		try {
			return direccionesClienteService.nuevo(direccion.getIdCliente(), direccion.getDireccion(), direccion.getNumero(), direccion.getEscalera(), direccion.getPiso(), direccion.getPuerta(), direccion.getLocalidad(), direccion.getProvincia(), direccion.getCodigoPostal());
		} catch (ServiceException e) {
			log.error(e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Obtiene un medio de comunicación de un cliente
	 * 
	 * @param idCliente
	 * @param idTipo
	 * @return medio de comunicación
	 */
	@CrossOrigin
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<DireccionCliente> get(@RequestParam(value = "idCliente") Long idCliente,
			@RequestParam(value = "idDireccion") Long idDireccion) {
		DireccionCliente direccionCliente = direccionesClienteService.get(idCliente, idDireccion);
		HttpStatus status = direccionCliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<DireccionCliente>(direccionCliente, status);
	}

	/**
	 * Actualiza un medio de comunicación de un cliente
	 * 
	 * @param medio parámetros de entrada [idCliente, idTipo, valor]
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void actualizar(@RequestBody ParamsDireccion direccion) throws ControllerException {
		try {
			direccionesClienteService.actualizar(direccion.getIdCliente(), direccion.getIdDireccion(), direccion.getDireccion(), direccion.getNumero(), direccion.getEscalera(), direccion.getPiso(), direccion.getPuerta(), direccion.getLocalidad(), direccion.getProvincia(), direccion.getCodigoPostal());
		} catch (ServiceException e) {
			log.error(e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Borra un medio de comunicación de un cliente
	 * 
	 * @param medio parámetros de entrada [idCliente, idTipo]
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void borrar(@RequestBody ParamsDireccion direccion) throws ControllerException {
		try {
			direccionesClienteService.borrar(direccion.getIdCliente(), direccion.getIdDireccion());
		} catch (ServiceException e) {
			log.error(e);
			throw new ControllerException(e);
		}
	}
}
