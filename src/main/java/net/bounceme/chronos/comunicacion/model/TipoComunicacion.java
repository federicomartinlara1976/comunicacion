package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;

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
 * TODO - Se puede usar el nombre de un identificador de Bean para determinar
 * el bean que se usará para enviar un aviso mediante este tipo
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TipoComunicacion [id=" + id + ", denominacion=" + denominacion
            + ", nombreClaseEmisora=" + nombreClaseEmisora + "]";
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denominacion == null) ? 0 : denominacion.hashCode());
		result = prime * result + ((icono == null) ? 0 : icono.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombreClaseEmisora == null) ? 0 : nombreClaseEmisora.hashCode());
		return result;
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
		if (denominacion == null) {
			if (other.denominacion != null) {
				return false;
			}
		} else if (!denominacion.equals(other.denominacion)) {
			return false;
		}
		if (icono == null) {
			if (other.icono != null) {
				return false;
			}
		} else if (!icono.equals(other.icono)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nombreClaseEmisora == null) {
			if (other.nombreClaseEmisora != null) {
				return false;
			}
		} else if (!nombreClaseEmisora.equals(other.nombreClaseEmisora)) {
			return false;
		}
		return true;
	}

    
}
