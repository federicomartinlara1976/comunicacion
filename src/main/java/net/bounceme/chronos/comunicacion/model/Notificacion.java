package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad que representa una notificación de aviso al cliente.
 * De esta forma si un medio fallase, se puede elegir otro y volver a
 * notificarle
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "NOTIFICACIONES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "NOTIFICACIONES")
public class Notificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588580781792975840L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Fecha y hora de creación
     */
    @Column
	private Date fechaHoraCreacion;
	
	/**
	 * Fecha y hora del envío
	 */
	@Column
	private Date fechaHoraEnvio;
	
	/**
	 * Indica si se ha logrado enviar el aviso o no
	 */
	@Column
	private String resultado;
	
	/**
	 * El aviso con la fecha de inicio de la obra
	 * y el medio de comunicación
	 */
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="idAviso", nullable=false, updatable=false)
	private Aviso aviso;
	
	/**
     * 
     */
    @JsonIgnore
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "idCliente"),
        @JoinColumn(name = "idTipoComunicacion")
    })
    private MedioComunicacionCliente datosMedioComunicacion;

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
	 * @return the fechaHoraEnvio
	 */
	public Date getFechaHoraEnvio() {
		return fechaHoraEnvio;
	}

	/**
	 * @param fechaHoraEnvio the fechaHoraEnvio to set
	 */
	public void setFechaHoraEnvio(Date fechaHoraEnvio) {
		this.fechaHoraEnvio = fechaHoraEnvio;
	}

	/**
	 * @return the aviso
	 */
	public Aviso getAviso() {
		return aviso;
	}

	/**
	 * @param aviso the aviso to set
	 */
	public void setAviso(Aviso aviso) {
		this.aviso = aviso;
	}

	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
     * @return the datosMedioComunicacion
     */
    public MedioComunicacionCliente getDatosMedioComunicacion() {
        return datosMedioComunicacion;
    }

    /**
     * @param datosMedioComunicacion the datosMedioComunicacion to set
     */
    public void setDatosMedioComunicacion(MedioComunicacionCliente datosMedioComunicacion) {
        this.datosMedioComunicacion = datosMedioComunicacion;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Notificacion [id=" + id + ", fechaHoraCreacion=" + fechaHoraCreacion
            + ", fechaHoraEnvio=" + fechaHoraEnvio + ", resultado=" + resultado + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aviso == null) ? 0 : aviso.hashCode());
        result = prime * result
            + ((datosMedioComunicacion == null) ? 0 : datosMedioComunicacion.hashCode());
        result = prime * result + ((fechaHoraCreacion == null) ? 0 : fechaHoraCreacion.hashCode());
        result = prime * result + ((fechaHoraEnvio == null) ? 0 : fechaHoraEnvio.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
        return result;
    }

    /* (non-Javadoc)
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
        Notificacion other = (Notificacion) obj;
        if (aviso == null) {
            if (other.aviso != null)
                return false;
        }
        else if (!aviso.equals(other.aviso))
            return false;
        if (datosMedioComunicacion == null) {
            if (other.datosMedioComunicacion != null)
                return false;
        }
        else if (!datosMedioComunicacion.equals(other.datosMedioComunicacion))
            return false;
        if (fechaHoraCreacion == null) {
            if (other.fechaHoraCreacion != null)
                return false;
        }
        else if (!fechaHoraCreacion.equals(other.fechaHoraCreacion))
            return false;
        if (fechaHoraEnvio == null) {
            if (other.fechaHoraEnvio != null)
                return false;
        }
        else if (!fechaHoraEnvio.equals(other.fechaHoraEnvio))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (resultado == null) {
            if (other.resultado != null)
                return false;
        }
        else if (!resultado.equals(other.resultado))
            return false;
        return true;
    }
}
