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

import net.bounceme.chronos.comunicacion.controllers.params.ParamsMedioComunicacion;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.services.MediosComunicacionClienteService;

/**
 * Controlador para la gestión de medios de comunicación de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/clientes/mediosComunicacion")
public class MediosComunicacionClienteController {
	Logger log = Logger.getLogger(MediosComunicacionClienteController.class);
	
	@Autowired
	@Qualifier(MediosComunicacionClienteService.NAME)
	private MediosComunicacionClienteService mediosComunicacionClienteService;

	/**
	 * Lista los medios para comunicarse con un cliente
	 * 
	 * @param idCliente
	 * @return listado de medios
	 */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	public List<MedioComunicacionCliente> listAll(@RequestParam(value = "idCliente") Long idCliente) {
		return mediosComunicacionClienteService.listar(idCliente);
	}

	/**
	 * @param medio parámetros de entrada [idCliente, idTipo, valor]
	 * @return medio creado
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public MedioComunicacionCliente nuevo(@RequestBody ParamsMedioComunicacion medio) throws ControllerException {
		try {
			return mediosComunicacionClienteService.nuevo(medio.getIdCliente(), medio.getIdTipo(), medio.getValor());
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
	public ResponseEntity<MedioComunicacionCliente> get(@RequestParam(value = "idCliente") Long idCliente,
			@RequestParam(value = "idTipo") Long idTipo) {
		MedioComunicacionCliente medioComunicacionCliente = mediosComunicacionClienteService.get(idCliente, idTipo);
		HttpStatus status = medioComunicacionCliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<MedioComunicacionCliente>(medioComunicacionCliente, status);
	}

	/**
	 * Actualiza un medio de comunicación de un cliente
	 * 
	 * @param medio parámetros de entrada [idCliente, idTipo, valor]
	 * @throws ControllerException
	 */
	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void actualizar(@RequestBody ParamsMedioComunicacion medio) throws ControllerException {
		try {
			mediosComunicacionClienteService.actualizar(medio.getIdCliente(), medio.getIdTipo(), medio.getValor());
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
	public void borrar(@RequestBody ParamsMedioComunicacion medio) throws ControllerException {
		try {
			mediosComunicacionClienteService.borrar(medio.getIdCliente(), medio.getIdTipo());
		} catch (ServiceException e) {
			log.error(e);
			throw new ControllerException(e);
		}
	}
}
