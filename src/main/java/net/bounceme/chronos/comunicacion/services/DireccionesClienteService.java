package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.exceptions.ServiceException;

public interface DireccionesClienteService {

	String NAME = "DireccionesClienteService";

	/**
	 * @param idCliente
	 * @param direccionClienteDTO
	 * @return
	 * @throws ServiceException
	 */
	DireccionClienteDTO nuevo(Long idCliente, DireccionClienteDTO direccionClienteDTO) throws ServiceException;

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @return
	 */
	DireccionClienteDTO get(Long idDireccion);

	
	/**
	 * @param direccionClienteDTO
	 * @throws ServiceException
	 */
	void actualizar(DireccionClienteDTO direccionClienteDTO) throws ServiceException;

	/**
	 * @param idDireccion
	 * @throws ServiceException
	 */
	void borrar(Long idDireccion) throws ServiceException;

	/**
	 * @param idCliente
	 * @return
	 */
	List<DireccionClienteDTO> listar(Long idCliente);

}
