package net.bounceme.chronos.comunicacion.common;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DireccionDTO implements Direccion, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1775117564075688057L;

	private String direccion;
	
	private String numero;
	
	private Integer piso;
	
	private String escalera;
	
	private String puerta;
	
	private String localidad;
	
	private String provincia;
	
	private String codigoPostal;

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
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
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
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(codigoPostal, direccion, escalera, localidad, numero, piso, provincia, puerta);
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
		if (!(obj instanceof DireccionDTO)) {
			return false;
		}
		DireccionDTO other = (DireccionDTO) obj;
		return Objects.equals(codigoPostal, other.codigoPostal) && Objects.equals(direccion, other.direccion)
				&& Objects.equals(escalera, other.escalera) && Objects.equals(localidad, other.localidad)
				&& Objects.equals(numero, other.numero) && Objects.equals(piso, other.piso)
				&& Objects.equals(provincia, other.provincia) && Objects.equals(puerta, other.puerta);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DireccionDTO [");
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
