package net.bounceme.chronos.comunicacion.services;

import java.util.Date;
import java.util.List;

import net.bounceme.chronos.comunicacion.dto.RegistroNotificacionDTO;

public interface RegistroNotificacionesService {
	
	List<RegistroNotificacionDTO> searchByDates(Date from, Date to);

	List<RegistroNotificacionDTO> searchByClient(Long idClient);
}
