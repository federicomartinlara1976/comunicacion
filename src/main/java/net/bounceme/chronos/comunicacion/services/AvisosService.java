package net.bounceme.chronos.comunicacion.services;

import java.util.Date;
import java.util.List;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.Aviso;

/**
 * Interfaz del servicio de gestión de avisos a clientes
 * 
 * @author frederik
 *
 */
public interface AvisosService {
    public static final String NAME = "avisosService";
    
    /**
     * Crea un nuevo aviso de inicio de obra para un cliente.
     * No se establece en este método el medio de comunicación
     * para el aviso.
     *  
     * @param idCliente el identificador del cliente
     * @param idDireccion el identificador de la direccion donde se hará la obra
     * @param fechaInicioObra
     * @param mensaje
     * @return el aviso creado
     * @throws ServiceException 
     */
    Aviso nuevoAviso(Long idCliente, Long idDireccion, Date fechaInicioObra, String mensaje) throws ServiceException;
    
    /**
     * Anula un aviso si no tiene notificaciones. Si tuviera notificaciones
     * enviadas no se borra el aviso
     * 
     * @param idAviso identificador del aviso
     * @throws ServiceException 
     */
    void anularAviso(Long idAviso) throws ServiceException;

	/**
	 * Obtiene un aviso
	 * 
	 * @param id
	 * @return
	 */
	Aviso get(Long id);

	/**
	 * @return
	 */
	List<Aviso> listar();
    
}
