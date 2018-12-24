package net.bounceme.chronos.comunicacion.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsAviso;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.services.AvisosService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/avisos")
public class AvisosController {
	Logger log = LoggerFactory.getLogger(AvisosController.class);

	@Autowired
	@Qualifier(AvisosService.NAME)
	private AvisosService avisosService;
	
	@CrossOrigin
	@GetMapping
	public List<Aviso> listAll() {
		return avisosService.listar();
	}

	/**
	 * Crea un nuevo aviso
	 * 
	 * @param aviso parámetros de creación del aviso
	 * @return el aviso creado
	 * @throws ControllerException
	 */
	@CrossOrigin
	@GetMapping(value = "/new")
	@ResponseStatus(HttpStatus.CREATED)
	public Aviso nuevo(@RequestBody @Valid ParamsAviso aviso) throws ControllerException {
		try {
			return avisosService.nuevoAviso(aviso.getIdCliente(), aviso.getIdDireccion(), aviso.getFechaInicioObra(), aviso.getMensaje());
		} catch (ServiceException e) {
			log.error("ERROR: ", e);
			throw new ControllerException(e);
		}
	}

	/**
	 * Obtiene un aviso
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<Aviso> get(@PathVariable Long id) {
		Aviso aviso = avisosService.get(id);
		HttpStatus status = aviso != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(aviso, status);
	}

	/**
	 * Borra un aviso
	 * 
	 * @param id identificador
	 * @throws ControllerException
	 */
	@CrossOrigin
	@GetMapping(value = "/{id}/delete")
	@ResponseStatus(HttpStatus.OK)
	public void anular(@PathVariable Long id) throws ControllerException {
		try {
			// Si tiene notificaciones enviadas, no se podrá borrar
			avisosService.anularAviso(id);
		} catch (ServiceException e) {
			log.error("ERROR: ", e);
			throw new ControllerException(e);
		}
	}
}
