package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;
import java.util.Date;
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

@Entity
@Table(name = "REGISTRO_NOTIFICACION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "REGISTRO_NOTIFICACION")
public class RegistroNotificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323776740630772201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="idCliente", nullable = true)
	private Cliente cliente;
	
	@OneToOne
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cliente, fechaHoraNotificacion, id, notificacion, resultado);
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
		if (!(obj instanceof RegistroNotificacion)) {
			return false;
		}
		RegistroNotificacion other = (RegistroNotificacion) obj;
		return Objects.equals(cliente, other.cliente)
				&& Objects.equals(fechaHoraNotificacion, other.fechaHoraNotificacion) && Objects.equals(id, other.id)
				&& Objects.equals(notificacion, other.notificacion) && Objects.equals(resultado, other.resultado);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistroNotificacion [");
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
		if (notificacion != null) {
			builder.append("notificacion=");
			builder.append(notificacion);
			builder.append(", ");
		}
		if (resultado != null) {
			builder.append("resultado=");
			builder.append(resultado);
			builder.append(", ");
		}
		if (fechaHoraNotificacion != null) {
			builder.append("fechaHoraNotificacion=");
			builder.append(fechaHoraNotificacion);
		}
		builder.append("]");
		return builder.toString();
	}
}
