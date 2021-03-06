package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad que representa una forma de comunicarse con el cliente
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "MEDIOS_COMUNICACION_CLIENTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "MEDIOS_COMUNICACION_CLIENTE")
public class MedioComunicacionCliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2396223420414145945L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * El tipo de comunicación (SMS, FAX, email)
	 */
	@ManyToOne
	@JoinColumn(name="idTipoComunicacion")
	private TipoComunicacion tipoComunicacion;
	
	/**
	 * Cliente al que pertenece este medio
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	/**
	 * Valor del medio (un número fijo, móvil o una dirección de correo electrónico)
	 */
	@Column
	private String valor;
	

	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public TipoComunicacion getTipoComunicacion() {
		return tipoComunicacion;
	}

	/**
	 * @param tipoComunicacion
	 */
	public void setTipoComunicacion(TipoComunicacion tipoComunicacion) {
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
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MedioComunicacionCliente [valor=" + valor + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		MedioComunicacionCliente other = (MedioComunicacionCliente) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		}
		else if (!cliente.equals(other.cliente))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		}
		else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
