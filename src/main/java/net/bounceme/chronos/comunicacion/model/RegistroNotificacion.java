package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REGISTRO_NOTIFICACION")
public class RegistroNotificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323776740630772201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idCliente", nullable = true)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idNotificacion", nullable = true)
	private Notificacion notificacion;
	
	@Column
	private String resultado;
	
	@Column
	private Date fechaHoraNotificacion;

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
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
		RegistroNotificacion other = (RegistroNotificacion) obj;
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
