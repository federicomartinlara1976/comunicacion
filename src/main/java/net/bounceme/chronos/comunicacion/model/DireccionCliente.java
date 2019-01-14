package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Objects;

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

import net.bounceme.chronos.comunicacion.common.Direccion;

/**
 * Entidad que representa una forma de comunicarse con el cliente
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "DIRECCIONES_CLIENTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "DIRECCIONES_CLIENTE")
public class DireccionCliente implements Direccion, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2396223420414145945L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Cliente al que pertenece este medio
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;

	@Column
	private String direccion;
	
	@Column
	private Integer numero;
	
	@Column
	private Integer piso;
	
	@Column
	private String escalera;
	
	@Column
	private String puerta;
	
	@Column
	private String localidad;
	
	@Column
	private String provincia;
	
	@Column
	private Integer codigoPostal;
	
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
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * @return the piso
	 */
	public Integer getPiso() {
		return piso;
	}

	/**
	 * @param piso the piso to set
	 */
	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	/**
	 * @return the escalera
	 */
	public String getEscalera() {
		return escalera;
	}

	/**
	 * @param escalera the escalera to set
	 */
	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	/**
	 * @return the puerta
	 */
	public String getPuerta() {
		return puerta;
	}

	/**
	 * @param puerta the puerta to set
	 */
	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	/**
	 * @return the localidad
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * @return the provincia
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia the provincia to set
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the codigoPostal
	 */
	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cliente, codigoPostal, direccion, escalera, id, localidad, numero, piso, provincia, puerta);
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
		if (!(obj instanceof DireccionCliente)) {
			return false;
		}
		DireccionCliente other = (DireccionCliente) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(codigoPostal, other.codigoPostal)
				&& Objects.equals(direccion, other.direccion) && Objects.equals(escalera, other.escalera)
				&& Objects.equals(id, other.id) && Objects.equals(localidad, other.localidad)
				&& Objects.equals(numero, other.numero) && Objects.equals(piso, other.piso)
				&& Objects.equals(provincia, other.provincia) && Objects.equals(puerta, other.puerta);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DireccionCliente [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (cliente != null) {
			builder.append("cliente=");
			builder.append(cliente);
			builder.append(", ");
		}
		if (direccion != null) {
			builder.append("direccion=");
			builder.append(direccion);
			builder.append(", ");
		}
		if (numero != null) {
			builder.append("numero=");
			builder.append(numero);
			builder.append(", ");
		}
		if (piso != null) {
			builder.append("piso=");
			builder.append(piso);
			builder.append(", ");
		}
		if (escalera != null) {
			builder.append("escalera=");
			builder.append(escalera);
			builder.append(", ");
		}
		if (puerta != null) {
			builder.append("puerta=");
			builder.append(puerta);
			builder.append(", ");
		}
		if (localidad != null) {
			builder.append("localidad=");
			builder.append(localidad);
			builder.append(", ");
		}
		if (provincia != null) {
			builder.append("provincia=");
			builder.append(provincia);
			builder.append(", ");
		}
		if (codigoPostal != null) {
			builder.append("codigoPostal=");
			builder.append(codigoPostal);
		}
		builder.append("]");
		return builder.toString();
	}
}
