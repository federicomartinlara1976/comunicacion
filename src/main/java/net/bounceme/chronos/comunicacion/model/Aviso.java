package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad de avisos del cliente, pensada para que el cliente
 * reciba un aviso por cada obra contratada
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "AVISOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "AVISOS")
public class Aviso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1518449193073022458L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private Date fechaInicioObra;
	
	@Column
	private String mensaje;
	
	@Column
	private Boolean estaNotificado;
	
	/**
	 * Este es el cliente para el cual se genera este aviso
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCliente", nullable = true)
	private Cliente cliente;
	
	/**
	 * Todas las notificaciones de este aviso. Si un medio fallase,
	 * la notificación indicará el fallo, con lo que se deberá
	 * escoger otro medio y volver a enviar el aviso
	 */
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "NOTIFICACIONES_AVISO")
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "aviso", targetEntity = Notificacion.class)
	private List<Notificacion> notificaciones;

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
	 * @return the fechaInicioObra
	 */
	public Date getFechaInicioObra() {
		return fechaInicioObra;
	}

	/**
	 * @param fechaInicioObra the fechaInicioObra to set
	 */
	public void setFechaInicioObra(Date fechaInicioObra) {
		this.fechaInicioObra = fechaInicioObra;
	}
	
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
     * @return the estaNotificado
     */
    public Boolean getEstaNotificado() {
        return estaNotificado;
    }

    /**
     * @param estaNotificado the estaNotificado to set
     */
    public void setEstaNotificado(Boolean estaNotificado) {
        this.estaNotificado = estaNotificado;
    }

    /**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the notificaciones
	 */
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones the notificaciones to set
	 */
	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = Collections.unmodifiableList(notificaciones);
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Aviso [id=" + id + ", fechaInicioObra=" + fechaInicioObra + ", mensaje=" + mensaje
            + ", estaNotificado=" + estaNotificado + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + ((estaNotificado == null) ? 0 : estaNotificado.hashCode());
        result = prime * result + ((fechaInicioObra == null) ? 0 : fechaInicioObra.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mensaje == null) ? 0 : mensaje.hashCode());
        result = prime * result + ((notificaciones == null) ? 0 : notificaciones.hashCode());
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
        Aviso other = (Aviso) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        }
        else if (!cliente.equals(other.cliente))
            return false;
        if (estaNotificado == null) {
            if (other.estaNotificado != null)
                return false;
        }
        else if (!estaNotificado.equals(other.estaNotificado))
            return false;
        if (fechaInicioObra == null) {
            if (other.fechaInicioObra != null)
                return false;
        }
        else if (!fechaInicioObra.equals(other.fechaInicioObra))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (mensaje == null) {
            if (other.mensaje != null)
                return false;
        }
        else if (!mensaje.equals(other.mensaje))
            return false;
        if (notificaciones == null) {
            if (other.notificaciones != null)
                return false;
        }
        else if (!notificaciones.equals(other.notificaciones))
            return false;
        return true;
    }
}
