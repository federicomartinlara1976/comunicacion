package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entidad para los tipos de comunicación.
 * 
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "TIPOS_COMUNICACION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "TIPOS_COMUNICACION")
public class TipoComunicacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7342690245310292143L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String denominacion;
	
	/**
	 * Nombre del bean que va a realizar el envío
	 */
	@Column
	private String nombreClaseEmisora;
	
	@Column
	private String icono;
	
	/**
	 * 
	 */
	public TipoComunicacion() {}

	/**
	 * @param denominacion
	 * @param nombreClaseEmisora
	 */
	public TipoComunicacion(String denominacion, String nombreClaseEmisora) {
		this.denominacion = denominacion;
		this.nombreClaseEmisora = nombreClaseEmisora;
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
	 * @return the denominacion
	 */
	public String getDenominacion() {
		return denominacion;
	}

	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	/**
     * @return the nombreClaseEmisora
     */
    public String getNombreClaseEmisora() {
        return nombreClaseEmisora;
    }

    /**
     * @param nombreClaseEmisora the nombreClaseEmisora to set
     */
    public void setNombreClaseEmisora(String nombreClaseEmisora) {
        this.nombreClaseEmisora = nombreClaseEmisora;
    }
    
    

    /**
	 * @return the icono
	 */
	public String getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public void setIcono(String icono) {
		this.icono = icono;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(denominacion, icono, id, nombreClaseEmisora);
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
		if (!(obj instanceof TipoComunicacion)) {
			return false;
		}
		TipoComunicacion other = (TipoComunicacion) obj;
		return Objects.equals(denominacion, other.denominacion) && Objects.equals(icono, other.icono)
				&& Objects.equals(id, other.id) && Objects.equals(nombreClaseEmisora, other.nombreClaseEmisora);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoComunicacion [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (denominacion != null) {
			builder.append("denominacion=");
			builder.append(denominacion);
			builder.append(", ");
		}
		if (nombreClaseEmisora != null) {
			builder.append("nombreClaseEmisora=");
			builder.append(nombreClaseEmisora);
			builder.append(", ");
		}
		if (icono != null) {
			builder.append("icono=");
			builder.append(icono);
		}
		builder.append("]");
		return builder.toString();
	}
}
