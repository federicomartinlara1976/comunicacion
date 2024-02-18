package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.AvisoDTO;

/**
 * Interfaz del servicio de gestión de avisos a clientes
 * 
 * @author frederik
 *
 */
public interface AvisosService {
    
	AvisoDTO save(AvisoDTO aviso);
    
    void anularAviso(AvisoDTO aviso);

	AvisoDTO findById(Long id);

	List<AvisoDTO> list();
	
	void delete(Long id);
    
}
