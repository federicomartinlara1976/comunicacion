package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	@OneToOne
	@JoinColumn(name="idTipoComunicacion")
	private TipoComunicacion tipoComunicacion;
	
	/**
	 * Cliente al que pertenece este medio
	 */
	@JsonIgnore
	@OneToOne
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cliente, id, tipoComunicacion, valor);
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
		if (!(obj instanceof MedioComunicacionCliente)) {
			return false;
		}
		MedioComunicacionCliente other = (MedioComunicacionCliente) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(id, other.id)
				&& Objects.equals(tipoComunicacion, other.tipoComunicacion) && Objects.equals(valor, other.valor);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MedioComunicacionCliente [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (tipoComunicacion != null) {
			builder.append("tipoComunicacion=");
			builder.append(tipoComunicacion);
			builder.append(", ");
		}
		if (cliente != null) {
			builder.append("cliente=");
			builder.append(cliente);
			builder.append(", ");
		}
		if (valor != null) {
			builder.append("valor=");
			builder.append(valor);
		}
		builder.append("]");
		return builder.toString();
	}
}
