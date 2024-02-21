package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;

/**
 * Interfaz del servicio que gestiona los tipos de comunicación 
 * 
 * @author frederik
 *
 */
public interface TiposComunicacionService {
	
	TipoComunicacionDTO save(TipoComunicacionDTO tipoComunicacionDTO);
	
	TipoComunicacionDTO findById(Long id);
	
	TipoComunicacionDTO findByName(String name);
	
	void delete(Long id);
	
	List<TipoComunicacionDTO> list();
}
