package net.bounceme.chronos.comunicacion.controllers.params;

public class ParamsDireccion {
	
	private Long idCliente;
	
	private Long idDireccion;

	private String direccion;
	
	private String numero;
	
	private Integer piso;
	
	private String escalera;
	
	private String puerta;
	
	private String localidad;
	
	private String provincia;
	
	private String codigoPostal;

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

	/**
	 * @return the direccion
	 */
	public String getAddress() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setAddress(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the numero
	 */
	public String getNumber() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumber(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the piso
	 */
	public Integer getFloor() {
		return piso;
	}

	/**
	 * @param piso the piso to set
	 */
	public void setFloor(Integer piso) {
		this.piso = piso;
	}

	/**
	 * @return the escalera
	 */
	public String getEsc() {
		return escalera;
	}

	/**
	 * @param escalera the escalera to set
	 */
	public void setEsc(String escalera) {
		this.escalera = escalera;
	}

	/**
	 * @return the puerta
	 */
	public String getDoor() {
		return puerta;
	}

	/**
	 * @param puerta the puerta to set
	 */
	public void setDoor(String puerta) {
		this.puerta = puerta;
	}

	/**
	 * @return the localidad
	 */
	public String getCity() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setCity(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * @return the provincia
	 */
	public String getProvince() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvince(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getPostalCode() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setPostalCode(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
