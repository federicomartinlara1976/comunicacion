package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entidad cliente
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "CLIENTES")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "CLIENTES")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 394031209826995192L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
	private String apellidos;

	@Column
	private String dni;

	/**
	 * Un cliente puede tener varias obras contratadas.
	 * Para cada obra se genera un aviso
	 * 
	 * FIXME - No se puede cachear esta colección de momento
	 * ya que no hace el borrado correctamente. Buscar la solución
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Aviso> avisos;

	/**
	 * A un cliente se le puede comunicar de varias formas, por si alguna fallase
	 * 
	 * FIXME - No se puede cachear esta colección de momento
	 * ya que no hace el borrado correctamente. Buscar la solución
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<MedioComunicacionCliente> mediosComunicacion;

	/**
	 * 
	 */
	public Cliente() {
	}

	public Cliente(String nombre, String apellidos, String dni) {
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
	public List<MedioComunicacionCliente> getMediosComunicacion() {
		return mediosComunicacion;
	}

	/**
	 * @param mediosComunicacion the mediosComunicacion to set
	 */
	public void setMediosComunicacion(List<MedioComunicacionCliente> mediosComunicacion) {
		this.mediosComunicacion = Collections.unmodifiableList(mediosComunicacion);
	}

	/**
	 * @return the avisos
	 */
	public List<Aviso> getAvisos() {
		return avisos;
	}

	/**
	 * @param avisos the avisos to set
	 */
	public void setAvisos(List<Aviso> avisos) {
		this.avisos = Collections.unmodifiableList(avisos);
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
		Cliente other = (Cliente) obj;
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
