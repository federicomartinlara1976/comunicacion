package net.bounceme.chronos.comunicacion.controllers.params;

public class ParamCliente {

	private String nombre;

	private String apellidos;

	private String dni;

	/**
	 * @return the nombre
	 */
	public String getName() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setName(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidos
	 */
	public String getLastName() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setLastName(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the dni
	 */
	public String getIdentification() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setIdentification(String dni) {
		this.dni = dni;
	}
}
