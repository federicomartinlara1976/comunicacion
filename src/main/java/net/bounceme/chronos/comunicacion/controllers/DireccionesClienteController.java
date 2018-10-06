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
	 * @param idCliente
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public List<DireccionCliente> listAll(@RequestParam(value = "idCliente") Long idCliente) {
		return direccionesClienteService.listar(idCliente);
	}
	
	/**
	 * @param direccion
	 * @return
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public DireccionCliente nuevo(@RequestBody ParamsDireccion direccion) throws ControllerException {
		try {
			return direccionesClienteService.nuevo(direccion.getIdCliente(), direccion.getDireccion(), direccion.getNumero(), direccion.getEscalera(), direccion.getPiso(), direccion.getPuerta(), direccion.getLocalidad(), direccion.getProvincia(), direccion.getCodigoPostal());
		} catch (ServiceException e) {
			log.error(e);
			throw new ControllerException(e);
		}
	}

	
	/**
	 * @param direccion
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ResponseEntity<DireccionCliente> get(@RequestBody ParamsDireccion direccion) {
		DireccionCliente direccionCliente = direccionesClienteService.get(direccion.getIdDireccion());
		HttpStatus status = direccionCliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<DireccionCliente>(direccionCliente, status);
	}

	
	/**
	 * @param direccion
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
	 * @param id
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void borrar(@RequestParam(value = "id") Long id) throws ControllerException {
		try {
			direccionesClienteService.borrar(id);
		} catch (ServiceException e) {
			log.error(e);
			throw new ControllerException(e);
		}
	}
}
