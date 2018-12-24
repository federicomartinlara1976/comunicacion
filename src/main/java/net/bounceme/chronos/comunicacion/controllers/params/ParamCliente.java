package net.bounceme.chronos.comunicacion.controllers.params;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ParamCliente implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -5979757674589031821L;

    private Long id;

	private String nombre;

	private String apellidos;

	private String dni;

	private List<AvisoDTO> avisos;

	private List<MedioComunicacionClienteDTO> mediosComunicacion;
	
	private List<DireccionClienteDTO> direcciones;

	/**
	 * 
	 */
	public ParamCliente() {
	}

	public ParamCliente(String nombre, String apellidos, String dni) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return the mediosComunicacion
	 */
	public List<MedioComunicacionClienteDTO> getMediosComunicacion() {
		return mediosComunicacion;
	}

	/**
	 * @param mediosComunicacion the mediosComunicacion to set
	 */
	public void setMediosComunicacion(List<MedioComunicacionClienteDTO> mediosComunicacion) {
		this.mediosComunicacion = Collections.unmodifiableList(mediosComunicacion);
	}

	/**
	 * @return the avisos
	 */
	public List<AvisoDTO> getAvisos() {
		return avisos;
	}

	/**
	 * @param avisos the avisos to set
	 */
	public void setAvisos(List<AvisoDTO> avisos) {
		this.avisos = Collections.unmodifiableList(avisos);
	}

	/**
	 * @return the direcciones
	 */
	public List<DireccionClienteDTO> getDirecciones() {
		return direcciones;
	}

	/**
	 * @param direcciones the direcciones to set
	 */
	public void setDirecciones(List<DireccionClienteDTO> direcciones) {
		this.direcciones = direcciones;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + "]";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParamCliente other = (ParamCliente) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		}
		else if (!apellidos.equals(other.apellidos))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		}
		else if (!dni.equals(other.dni))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		}
		else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}
