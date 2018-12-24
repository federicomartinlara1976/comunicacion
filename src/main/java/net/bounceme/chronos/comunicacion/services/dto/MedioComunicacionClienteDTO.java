package net.bounceme.chronos.comunicacion.services.dto;

import java.io.Serializable;

public class MedioComunicacionClienteDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -5478206613950016516L;

    private Long id;
	
	private TipoComunicacionDTO tipoComunicacion;
	
	private ClienteDTO cliente;

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
	public TipoComunicacionDTO getTipoComunicacion() {
		return tipoComunicacion;
	}

	/**
	 * @param tipoComunicacion
	 */
	public void setTipoComunicacion(TipoComunicacionDTO tipoComunicacion) {
		this.tipoComunicacion = tipoComunicacion;
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
		MedioComunicacionClienteDTO other = (MedioComunicacionClienteDTO) obj;
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
