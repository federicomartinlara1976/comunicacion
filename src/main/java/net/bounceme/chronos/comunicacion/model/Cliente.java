package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<DireccionCliente> direcciones;

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

	/**
	 * @return the direcciones
	 */
	public List<DireccionCliente> getDirecciones() {
		return direcciones;
	}

	/**
	 * @param direcciones the direcciones to set
	 */
	public void setDirecciones(List<DireccionCliente> direcciones) {
		this.direcciones = direcciones;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, avisos, direcciones, dni, id, mediosComunicacion, nombre);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cliente)) {
			return false;
		}
		Cliente other = (Cliente) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(avisos, other.avisos)
				&& Objects.equals(direcciones, other.direcciones) && Objects.equals(dni, other.dni)
				&& Objects.equals(id, other.id) && Objects.equals(mediosComunicacion, other.mediosComunicacion)
				&& Objects.equals(nombre, other.nombre);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (nombre != null) {
			builder.append("nombre=");
			builder.append(nombre);
			builder.append(", ");
		}
		if (apellidos != null) {
			builder.append("apellidos=");
			builder.append(apellidos);
			builder.append(", ");
		}
		if (dni != null) {
			builder.append("dni=");
			builder.append(dni);
			builder.append(", ");
		}
		if (avisos != null) {
			builder.append("avisos=");
			builder.append(avisos);
			builder.append(", ");
		}
		if (mediosComunicacion != null) {
			builder.append("mediosComunicacion=");
			builder.append(mediosComunicacion);
			builder.append(", ");
		}
		if (direcciones != null) {
			builder.append("direcciones=");
			builder.append(direcciones);
		}
		builder.append("]");
		return builder.toString();
	}
}
