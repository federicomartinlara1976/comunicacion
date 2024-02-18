package net.bounceme.chronos.comunicacion.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

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
import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.services.TiposComunicacionService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/tiposComunicacion")
@Slf4j
public class TiposComunicacionController {

	@Autowired
	private TiposComunicacionService tiposComunicacionService;

	/**
	 * Devuelve un listado de los tipos de comunicación
	 * 
	 * @return listado
	 */
	@CrossOrigin
	@GetMapping("/")
	public List<TipoComunicacionDTO> listAll() {
		return tiposComunicacionService.list();
	}

	@CrossOrigin
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody TipoComunicacionDTO tipoComunicacionDTO) {
		Map<String, Object> response = new HashMap<>();

		tipoComunicacionDTO = tiposComunicacionService.save(tipoComunicacionDTO);

		log.info("Creado: {}", tipoComunicacionDTO.toString());
		response.put("mensaje", "El tipo de comunicación ha sido creado con éxito");
		response.put("tipoComunicacion", tipoComunicacionDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		TipoComunicacionDTO tipoComunicacionDTO = tiposComunicacionService.findById(id);

		if (Objects.isNull(tipoComunicacionDTO)) {
			response.put("mensaje", String.format("El tipo de comunicación con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TipoComunicacionDTO>(tipoComunicacionDTO, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> actualizar(@Valid @RequestBody TipoComunicacionDTO tipoComunicacionDTO, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		TipoComunicacionDTO tipoComunicacionActual = tiposComunicacionService.findById(id);
		if (Objects.isNull(tipoComunicacionActual)) {
			response.put("mensaje", String.format("El tipo de comunicación con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		tipoComunicacionActual.setDenominacion(tipoComunicacionDTO.getDenominacion());
		tipoComunicacionActual.setIcono(tipoComunicacionDTO.getIcono());
		tipoComunicacionActual.setNombreClaseEmisora(tipoComunicacionDTO.getNombreClaseEmisora());

		tipoComunicacionActual = tiposComunicacionService.save(tipoComunicacionActual);

		response.put("mensaje", "El tipo de comunicación ha sido actualizado con éxito");
		response.put("tipoComunicacion", tipoComunicacionActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		tiposComunicacionService.delete(id);

		response.put("mensaje", "El tipo de comunicación ha sido borrado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
