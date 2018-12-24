package net.bounceme.chronos.comunicacion.services.dto;

import java.io.Serializable;
import java.util.Date;

public class RegistroNotificacionDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 3554328122577839376L;

    private Long id;
	
	private ClienteDTO cliente;
	
	private NotificacionDTO notificacion;
	
	private String resultado;
	
	private Date fechaHoraNotificacion;

	public NotificacionDTO getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NotificacionDTO notificacion) {
		this.notificacion = notificacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Date getFechaHoraNotificacion() {
		return fechaHoraNotificacion;
	}

	public void setFechaHoraNotificacion(Date fechaHoraNotificacion) {
		this.fechaHoraNotificacion = fechaHoraNotificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fechaHoraNotificacion == null) ? 0 : fechaHoraNotificacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notificacion == null) ? 0 : notificacion.hashCode());
		result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroNotificacionDTO other = (RegistroNotificacionDTO) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (fechaHoraNotificacion == null) {
			if (other.fechaHoraNotificacion != null)
				return false;
		} else if (!fechaHoraNotificacion.equals(other.fechaHoraNotificacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notificacion == null) {
			if (other.notificacion != null)
				return false;
		} else if (!notificacion.equals(other.notificacion))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistroNotificacion [id=" + id + ", cliente=" + cliente + ", notificacion=" + notificacion
				+ ", resultado=" + resultado + ", fechaHoraNotificacion=" + fechaHoraNotificacion + "]";
	}
}
