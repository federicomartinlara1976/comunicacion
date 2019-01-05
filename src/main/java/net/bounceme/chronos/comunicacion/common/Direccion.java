package net.bounceme.chronos.comunicacion.common;

public interface Direccion {
	/**
	 * @return the direccion
	 */
	public String getDireccion();

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion);

	/**
	 * @return the numero
	 */
	public String getNumero();

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero);

	/**
	 * @return the piso
	 */
	public Integer getPiso();

	/**
	 * @param piso the piso to set
	 */
	public void setPiso(Integer piso);

	/**
	 * @return the escalera
	 */
	public String getEscalera();

	/**
	 * @param escalera the escalera to set
	 */
	public void setEscalera(String escalera);

	/**
	 * @return the puerta
	 */
	public String getPuerta();

	/**
	 * @param puerta the puerta to set
	 */
	public void setPuerta(String puerta);

	/**
	 * @return the localidad
	 */
	public String getLocalidad();

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(String localidad);

	/**
	 * @return the provincia
	 */
	public String getProvincia();

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(String provincia);

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal();

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal);
}
