package net.bounceme.chronos.comunicacion.services;

import java.util.List;

import net.bounceme.chronos.comunicacion.exceptions.ServiceException;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;

public interface DireccionesClienteService {

	String NAME = "DireccionesClienteService";

	MedioComunicacionCliente nuevo(Long idCliente, String direccion, String numero, String escalera, Integer piso,
			String puerta, String localidad, String provincia, String codigoPostal) throws ServiceException;

	DireccionCliente get(Long idCliente, Long idDireccion);

	void actualizar(Long idCliente, Long idDireccion, String direccion, String numero, String escalera, Integer piso,
			String puerta, String localidad, String provincia, String codigoPostal) throws ServiceException;

	void borrar(Long idCliente, Long idDireccion) throws ServiceException;

	List<DireccionCliente> listar(Long idCliente);

}
