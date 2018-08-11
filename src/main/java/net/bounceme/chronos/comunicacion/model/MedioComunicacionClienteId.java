package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Esta clase sirve como clave primaria compuesta para
 * la clase MediosComunicacionCliente, con el propósito
 * de no definir dos claves foráneas en dicha clase y evitar
 * el Anemic Domain Model
 * 
 * @author frederik
 *
 */
@Embeddable
public class MedioComunicacionClienteId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8523636235571729470L;

	/**
	 * Cliente al que pertenece este medio
	 */
	@JsonIgnore
	@Column(name="idCliente", insertable = false, updatable = false)
	private Cliente cliente;
	
	/**
	 * El tipo de comunicación (SMS, FAX, email)
	 */
	@Column(name="idTipoComunicacion", insertable = false, updatable = false)
	private TipoComunicacion tipoComunicacion;

	/**
	 * 
	 */
	public MedioComunicacionClienteId() {}

	/**
	 * @param cliente
	 * @param tipoComunicacion
	 */
	public MedioComunicacionClienteId(Cliente cliente, TipoComunicacion tipoComunicacion) {
		this.cliente = cliente;
		this.tipoComunicacion = tipoComunicacion;
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
	 * @return the tipoComunicacion
	 */
	public TipoComunicacion getTipoComunicacion() {
		return tipoComunicacion;
	}

	/**
	 * @param tipoComunicacion the tipoComunicacion to set
	 */
	public void setTipoComunicacion(TipoComunicacion tipoComunicacion) {
		this.tipoComunicacion = tipoComunicacion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ComunicacionesClienteId [cliente=" + cliente + ", tipoComunicacion=" + tipoComunicacion + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((tipoComunicacion == null) ? 0 : tipoComunicacion.hashCode());
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
		MedioComunicacionClienteId other = (MedioComunicacionClienteId) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		}
		else if (!cliente.equals(other.cliente))
			return false;
		if (tipoComunicacion == null) {
			if (other.tipoComunicacion != null)
				return false;
		}
		else if (!tipoComunicacion.equals(other.tipoComunicacion))
			return false;
		return true;
	}
}
