package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad de avisos del cliente, pensada para que el cliente
 * reciba un aviso por cada obra contratada
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "AVISOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "AVISOS")
public class Aviso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1518449193073022458L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private Date fechaInicioObra;
	
	@Column
	private String mensaje;
	
	@Column
	private Boolean estaNotificado;
	
	/**
	 * Este es el cliente para el cual se genera este aviso
	 */
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="idCliente", nullable = true)
	private Cliente cliente;

	/**
	 * Esta es la direccion del cliente para el cual se genera este aviso
	 */
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="idDireccion", nullable = true)
	private DireccionCliente direccionCliente;
	
	/**
	 * Todas las notificaciones de este aviso. Si un medio fallase,
	 * la notificación indicará el fallo, con lo que se deberá
	 * escoger otro medio y volver a enviar el aviso
	 */
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "NOTIFICACIONES_AVISO")
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "aviso", targetEntity = Notificacion.class)
	private List<Notificacion> notificaciones;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fechaInicioObra
	 */
	public Date getFechaInicioObra() {
		return fechaInicioObra;
	}

	/**
	 * @param fechaInicioObra the fechaInicioObra to set
	 */
	public void setFechaInicioObra(Date fechaInicioObra) {
		this.fechaInicioObra = fechaInicioObra;
	}
	
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
     * @return the estaNotificado
     */
    public Boolean getEstaNotificado() {
        return estaNotificado;
    }

    /**
     * @param estaNotificado the estaNotificado to set
     */
    public void setEstaNotificado(Boolean estaNotificado) {
        this.estaNotificado = estaNotificado;
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
	 * @return the direccionCliente
	 */
	public DireccionCliente getDireccionCliente() {
		return direccionCliente;
	}

	/**
	 * @param direccionCliente the direccionCliente to set
	 */
	public void setDireccionCliente(DireccionCliente direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	/**
	 * @return the notificaciones
	 */
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @param notificaciones the notificaciones to set
	 */
	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = Collections.unmodifiableList(notificaciones);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cliente, direccionCliente, estaNotificado, fechaInicioObra, id, mensaje, notificaciones);
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
		if (!(obj instanceof Aviso)) {
			return false;
		}
		Aviso other = (Aviso) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(direccionCliente, other.direccionCliente)
				&& Objects.equals(estaNotificado, other.estaNotificado)
				&& Objects.equals(fechaInicioObra, other.fechaInicioObra) && Objects.equals(id, other.id)
				&& Objects.equals(mensaje, other.mensaje) && Objects.equals(notificaciones, other.notificaciones);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Aviso [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (fechaInicioObra != null) {
			builder.append("fechaInicioObra=");
			builder.append(fechaInicioObra);
			builder.append(", ");
		}
		if (mensaje != null) {
			builder.append("mensaje=");
			builder.append(mensaje);
			builder.append(", ");
		}
		if (estaNotificado != null) {
			builder.append("estaNotificado=");
			builder.append(estaNotificado);
			builder.append(", ");
		}
		if (cliente != null) {
			builder.append("cliente=");
			builder.append(cliente);
			builder.append(", ");
		}
		if (direccionCliente != null) {
			builder.append("direccionCliente=");
			builder.append(direccionCliente);
			builder.append(", ");
		}
		if (notificaciones != null) {
			builder.append("notificaciones=");
			builder.append(notificaciones);
		}
		builder.append("]");
		return builder.toString();
	}
}
