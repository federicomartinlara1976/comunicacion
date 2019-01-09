package net.bounceme.chronos.comunicacion.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsMedioComunicacion;
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.services.MediosComunicacionClienteService;

/**
 * Controlador para la gestión de medios de comunicación de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/api/clientes/mediosComunicacion")
public class MediosComunicacionClienteController {
	private static final String ERROR = "ERROR: ";

	Logger log = LoggerFactory.getLogger(MediosComunicacionClienteController.class);
	
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
	@GetMapping
	public List<MedioComunicacionClienteDTO> listAll(@RequestParam(value = "idCliente") Long idCliente) {
		return mediosComunicacionClienteService.listar(idCliente);
	}

	/**
	 * @param medio parámetros de entrada [idCliente, idTipo, valor]
	 * @return medio creado
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PostMapping(value = "/new")
	@ResponseStatus(HttpStatus.CREATED)
	public MedioComunicacionClienteDTO nuevo(@RequestBody ParamsMedioComunicacion medio) throws ControllerException {
		try {
			return mediosComunicacionClienteService.nuevo(medio.getIdCliente(), medio.getIdTipo(), medio.getValor());
		} catch (ServiceException e) {
			log.error(ERROR, e);
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
	@PostMapping(value = "/get")
	public ResponseEntity<MedioComunicacionClienteDTO> get(@RequestBody ParamsMedioComunicacion medio) {
		MedioComunicacionClienteDTO medioComunicacionCliente = mediosComunicacionClienteService.get(medio.getIdCliente(), medio.getIdTipo());
		HttpStatus status = medioComunicacionCliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(medioComunicacionCliente, status);
	}

	/**
	 * Actualiza un medio de comunicación de un cliente
	 * 
	 * @param medio parámetros de entrada [idCliente, idTipo, valor]
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PutMapping(value = "/update")
	public void actualizar(@RequestBody ParamsMedioComunicacion medio) throws ControllerException {
		try {
			mediosComunicacionClienteService.actualizar(medio.getIdCliente(), medio.getIdTipo(), medio.getValor());
		} catch (ServiceException e) {
			log.error(ERROR, e);
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
	@DeleteMapping(value = "/delete")
	public void borrar(@RequestParam(value = "idCliente") Long idCliente, @RequestParam(value = "idTipo") Long idTipo) throws ControllerException {
		try {
			mediosComunicacionClienteService.borrar(idCliente, idTipo);
		} catch (ServiceException e) {
			log.error(ERROR, e);
			throw new ControllerException(e);
		}
	}
}
