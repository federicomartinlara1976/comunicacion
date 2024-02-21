package net.bounceme.chronos.comunicacion.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.AvisoDTO;
import net.bounceme.chronos.comunicacion.services.AvisosService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/avisos")
@Slf4j
public class AvisosController {
	
	@Autowired
	private AvisosService avisosService;
	
	@CrossOrigin
	@GetMapping("/")
	public List<AvisoDTO> listAll() {
		return avisosService.list();
	}

	@CrossOrigin
	@PostMapping(value = "/{id}/new")
	public ResponseEntity<?> create(@PathVariable Long id, @RequestBody AvisoDTO aviso) {
		Map<String, Object> response = new HashMap<>();

		aviso = avisosService.save(id, aviso);

		log.info("Creado: {}", aviso.toString());
		response.put("mensaje", "El aviso ha sido creado con éxito");
		response.put("aviso", aviso);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 * Obtiene un aviso
	 * 
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		AvisoDTO aviso = avisosService.findById(id);

		if (Objects.isNull(aviso)) {
			response.put("mensaje", String.format("El aviso con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AvisoDTO>(aviso, HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		avisosService.delete(id);

		response.put("mensaje", "El aviso ha sido borrado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
