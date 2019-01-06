package net.bounceme.chronos.comunicacion.controllers.params;

import net.bounceme.chronos.comunicacion.common.DireccionDTO;

public class ParamsDireccion extends DireccionDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3878699159679015638L;

	private Long idCliente;
	
	private Long idDireccion;

	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the idDireccion
	 */
	public Long getIdDireccion() {
		return idDireccion;
	}

	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
}
