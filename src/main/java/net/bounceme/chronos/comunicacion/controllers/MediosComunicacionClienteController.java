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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.services.MediosComunicacionClienteService;

/**
 * Controlador para la gestión de medios de comunicación de clientes
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/mediosComunicacionCliente")
@Slf4j
public class MediosComunicacionClienteController {
	
	@Autowired
	private MediosComunicacionClienteService mediosComunicacionClienteService;

	@CrossOrigin
	@GetMapping("/{id}")
	public List<MedioComunicacionClienteDTO> listDirecciones(@PathVariable Long id) {
		return mediosComunicacionClienteService.list(id);
	}
	
	@CrossOrigin
	@PostMapping(value = "/{id}/new")
	public ResponseEntity<?> nuevaDireccion(@PathVariable Long id, @RequestBody MedioComunicacionClienteDTO medioComunicacionClienteDTO) {
		Map<String, Object> response = new HashMap<>();

		medioComunicacionClienteDTO = mediosComunicacionClienteService.save(id, medioComunicacionClienteDTO);

		log.info("Creado: {}", medioComunicacionClienteDTO.toString());
		response.put("mensaje", "El medio se ha creado correctamente");
		response.put("medio", medioComunicacionClienteDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		MedioComunicacionClienteDTO medioComunicacionClienteDTO = mediosComunicacionClienteService.findById(id);
		HttpStatus status = medioComunicacionClienteDTO != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(medioComunicacionClienteDTO, status);
	}

	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, MedioComunicacionClienteDTO medioComunicacionClienteDTO) {
		Map<String, Object> response = new HashMap<>();

		MedioComunicacionClienteDTO medioComunicacionClienteActual = mediosComunicacionClienteService.findById(id);
		if (Objects.isNull(medioComunicacionClienteActual)) {
			response.put("mensaje", String.format("La dirección con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		medioComunicacionClienteActual.setValor(medioComunicacionClienteDTO.getValor());

		medioComunicacionClienteActual = mediosComunicacionClienteService.save(medioComunicacionClienteActual);

		response.put("mensaje", "El medio ha sido actualizado con éxito");
		response.put("direccion", medioComunicacionClienteActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		mediosComunicacionClienteService.delete(id);

		response.put("mensaje", "El medio ha sido borrado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
