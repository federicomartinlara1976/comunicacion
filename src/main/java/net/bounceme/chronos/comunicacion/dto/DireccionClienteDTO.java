package net.bounceme.chronos.comunicacion.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.bounceme.chronos.comunicacion.common.DireccionDTO;

@JsonInclude(Include.NON_NULL)
public class DireccionClienteDTO extends DireccionDTO {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long id;
	
	private ClienteDTO cliente;
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cliente, id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DireccionClienteDTO)) {
			return false;
		}
		DireccionClienteDTO other = (DireccionClienteDTO) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(id, other.id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DireccionClienteDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (cliente != null) {
			builder.append("cliente=");
			builder.append(cliente);
		}
		builder.append("]");
		return builder.toString();
	}
}
