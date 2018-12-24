package net.bounceme.chronos.comunicacion.services.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AvisoDTO implements Serializable {

	
	/**
     * 
     */
    private static final long serialVersionUID = -6309184769212090435L;

    private Long id;

	private Date fechaInicioObra;
	
	private String mensaje;
	
	private Boolean estaNotificado;
	
	private ClienteDTO cliente;

	private DireccionClienteDTO direccionCliente;
	
	private List<NotificacionDTO> notificaciones;

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
	public ClienteDTO getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the direccionCliente
	 */
	public DireccionClienteDTO getDireccionCliente() {
		return direccionCliente;
	}

	/**
	 * @param direccionCliente the direccionCliente to set
	 */
	public void setDireccionCliente(DireccionClienteDTO direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	/**
	 * @return the notificaciones
	 */
	public List<NotificacionDTO> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones the notificaciones to set
	 */
	public void setNotificaciones(List<NotificacionDTO> notificaciones) {
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
        result = prime * result + ((direccionCliente == null) ? 0 : direccionCliente.hashCode());
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
        AvisoDTO other = (AvisoDTO) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        }
        else if (!cliente.equals(other.cliente))
            return false;
        
        if (direccionCliente == null) {
            if (other.direccionCliente != null)
                return false;
        }
        else if (!direccionCliente.equals(other.direccionCliente))
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
