package net.bounceme.chronos.comunicacion.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import net.bounceme.chronos.comunicacion.dto.DateRangeDTO;
import net.bounceme.chronos.comunicacion.dto.RegistroNotificacionDTO;
import net.bounceme.chronos.comunicacion.exceptions.ControllerException;
import net.bounceme.chronos.comunicacion.services.RegistroNotificacionesService;

/**
 * Controlador que gestiona los tipos de comunicaciones
 * 
 * @author frederik
 *
 */
@RestController
@RequestMapping("/registro/notificaciones")
public class RegistroNotificacionesController {
	
	@Autowired
	private SimpleDateFormat dateFormat;
	
	@Autowired
	private RegistroNotificacionesService registroNotificacionesService;

	
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public List<RegistroNotificacionDTO> listarPorCliente(@PathVariable Long id) {
		return registroNotificacionesService.searchByClient(id);
	}

	/**
	 * Envia una notificación.
	 * 
	 * @param id identificador del cliente
	 * @throws ControllerException
	 */
	@CrossOrigin
	@PostMapping(value = "/fecha")
	@SneakyThrows
	public List<RegistroNotificacionDTO> listarPorFecha(@RequestBody DateRangeDTO dateRange) {
		String from = dateRange.getFrom();
		String to = dateRange.getTo();
		
		Date dFrom = dateFormat.parse(from);
		Date dTo = dateFormat.parse(to);
		
		return registroNotificacionesService.searchByDates(dFrom, dTo);
	}
}
