package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;

public interface DireccionesClienteService {

	String NAME = "DireccionesClienteService";

	/**
	 * @param idCliente
	 * @param direccion
	 * @param numero
	 * @param escalera
	 * @param piso
	 * @param puerta
	 * @param localidad
	 * @param provincia
	 * @param codigoPostal
	 * @return
	 * @throws ServiceException
	 */
	DireccionCliente nuevo(Long idCliente, String direccion, String numero, String escalera, Integer piso,
			String puerta, String localidad, String provincia, String codigoPostal) throws ServiceException;

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @return
	 */
	DireccionCliente get(Long idCliente, Long idDireccion);

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @param direccion
	 * @param numero
	 * @param escalera
	 * @param piso
	 * @param puerta
	 * @param localidad
	 * @param provincia
	 * @param codigoPostal
	 * @throws ServiceException
	 */
	void actualizar(Long idCliente, Long idDireccion, String direccion, String numero, String escalera, Integer piso,
			String puerta, String localidad, String provincia, String codigoPostal) throws ServiceException;

	/**
	 * @param idCliente
	 * @param idDireccion
	 * @throws ServiceException
	 */
	void borrar(Long idCliente, Long idDireccion) throws ServiceException;

	/**
	 * @param idCliente
	 * @return
	 */
	List<DireccionCliente> listar(Long idCliente);

}
