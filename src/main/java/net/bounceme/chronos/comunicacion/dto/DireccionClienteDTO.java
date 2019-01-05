package net.bounceme.chronos.comunicacion.dto;

import java.io.Serializable;

import net.bounceme.chronos.comunicacion.common.Direccion;

public class DireccionClienteDTO implements Direccion, Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
	
	private ClienteDTO cliente;

	private String direccion;
	
	private String numero;
	
	private Integer piso;
	
	private String escalera;
	
	private String puerta;
	
	private String localidad;
	
	private String provincia;
	
	private String codigoPostal;
	
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Direccion: " + direccion + ", nº " + numero
				+ ", " + piso + ", " + escalera + ", " + puerta + "\n" + localidad
				+ "\n" + codigoPostal + " " + provincia ;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((escalera == null) ? 0 : escalera.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((localidad == null) ? 0 : localidad.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((piso == null) ? 0 : piso.hashCode());
		result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
		result = prime * result + ((puerta == null) ? 0 : puerta.hashCode());
		return result;
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
		if (!(obj instanceof DireccionClienteDTO)) {
			return false;
		}
		DireccionClienteDTO other = (DireccionClienteDTO) obj;
		if (cliente == null) {
			if (other.cliente != null) {
				return false;
			}
		} else if (!cliente.equals(other.cliente)) {
			return false;
		}
		if (codigoPostal == null) {
			if (other.codigoPostal != null) {
				return false;
			}
		} else if (!codigoPostal.equals(other.codigoPostal)) {
			return false;
		}
		if (direccion == null) {
			if (other.direccion != null) {
				return false;
			}
		} else if (!direccion.equals(other.direccion)) {
			return false;
		}
		if (escalera == null) {
			if (other.escalera != null) {
				return false;
			}
		} else if (!escalera.equals(other.escalera)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (localidad == null) {
			if (other.localidad != null) {
				return false;
			}
		} else if (!localidad.equals(other.localidad)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		if (piso == null) {
			if (other.piso != null) {
				return false;
			}
		} else if (!piso.equals(other.piso)) {
			return false;
		}
		if (provincia == null) {
			if (other.provincia != null) {
				return false;
			}
		} else if (!provincia.equals(other.provincia)) {
			return false;
		}
		if (puerta == null) {
			if (other.puerta != null) {
				return false;
			}
		} else if (!puerta.equals(other.puerta)) {
			return false;
		}
		return true;
	}
}
