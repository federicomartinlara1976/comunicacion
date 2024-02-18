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
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.services.DireccionesClienteService;

@RestController
@RequestMapping("/direccionesCliente")
@Slf4j
public class DireccionesClienteController {
	
	@Autowired
	private DireccionesClienteService direccionesClienteService;
	
	@CrossOrigin
	@GetMapping("/{id}")
	public List<DireccionClienteDTO> listDirecciones(@PathVariable Long id) {
		return direccionesClienteService.list(id);
	}
	
	@CrossOrigin
	@PostMapping(value = "/{id}/new")
	public ResponseEntity<?> nuevaDireccion(@PathVariable Long id, @RequestBody DireccionClienteDTO direccionClienteDTO) {
		Map<String, Object> response = new HashMap<>();

		direccionClienteDTO = direccionesClienteService.save(id, direccionClienteDTO);

		log.info("Creada: {}", direccionClienteDTO.toString());
		response.put("mensaje", "La dirección se ha creado correctamente");
		response.put("direccion", direccionClienteDTO);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		DireccionClienteDTO direccionCliente = direccionesClienteService.findById(id);
		HttpStatus status = direccionCliente != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(direccionCliente, status);
	}

	@CrossOrigin
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, DireccionClienteDTO direccionClienteDTO) {
		Map<String, Object> response = new HashMap<>();

		DireccionClienteDTO direccionActual = direccionesClienteService.findById(id);
		if (Objects.isNull(direccionActual)) {
			response.put("mensaje", String.format("La dirección con ID %d no existe", id));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		direccionActual.setDireccion(direccionClienteDTO.getDireccion());
		direccionActual.setNumero(direccionClienteDTO.getNumero());
		direccionActual.setEscalera(direccionClienteDTO.getEscalera());
		direccionActual.setPiso(direccionClienteDTO.getPiso());
		direccionActual.setPuerta(direccionClienteDTO.getPuerta());
		direccionActual.setCodigoPostal(direccionClienteDTO.getCodigoPostal());
		direccionActual.setLocalidad(direccionClienteDTO.getLocalidad());
		direccionActual.setProvincia(direccionClienteDTO.getProvincia());

		direccionActual = direccionesClienteService.save(direccionActual);

		response.put("mensaje", "La dirección ha sido actualizada con éxito");
		response.put("direccion", direccionActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@CrossOrigin
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> borrar(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		direccionesClienteService.delete(id);

		response.put("mensaje", "La dirección ha sido borrada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
