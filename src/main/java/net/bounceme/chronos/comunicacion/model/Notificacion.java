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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidad que representa una notificación de aviso al cliente.
 * De esta forma si un medio fallase, se puede elegir otro y volver a
 * notificarle
 * 
 * @author frederik
 *
 */
@Entity
@Table(name = "NOTIFICACIONES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "NOTIFICACIONES")
public class Notificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6588580781792975840L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
     * Fecha y hora de creación
     */
    @Column
	private Date fechaHoraCreacion;
	
	/**
	 * Fecha y hora del envío
	 */
	@Column
	private Date fechaHoraEnvio;
	
	/**
	 * Indica si se ha logrado enviar el aviso o no
	 */
	@Column
	private String resultado;
	
	@Column
	private Integer reintentos;
	
	@Column
	private String estado;
	
	/**
	 * El aviso con la fecha de inicio de la obra
	 * y el medio de comunicación
	 */
	@JsonIgnore
	@OneToOne(optional=false)
	@JoinColumn(name="idAviso", nullable=false, updatable=false)
	private Aviso aviso;
	
	/**
     * 
     */
    @JsonIgnore
    @OneToOne
    private MedioComunicacionCliente datosMedioComunicacion;

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
	 * @return the fechaHoraEnvio
	 */
	public Date getFechaHoraEnvio() {
		return fechaHoraEnvio;
	}

	/**
	 * @param fechaHoraEnvio the fechaHoraEnvio to set
	 */
	public void setFechaHoraEnvio(Date fechaHoraEnvio) {
		this.fechaHoraEnvio = fechaHoraEnvio;
	}

	/**
	 * @return the reintentos
	 */
	public Integer getReintentos() {
		return reintentos;
	}

	/**
	 * @param reintentos the reintentos to set
	 */
	public void setReintentos(Integer reintentos) {
		this.reintentos = reintentos;
	}

	/**
	 * @return the fechaHoraCreacion
	 */
	public Date getFechaHoraCreacion() {
		return fechaHoraCreacion;
	}

	/**
	 * @param fechaHoraCreacion the fechaHoraCreacion to set
	 */
	public void setFechaHoraCreacion(Date fechaHoraCreacion) {
		this.fechaHoraCreacion = fechaHoraCreacion;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the aviso
	 */
	public Aviso getAviso() {
		return aviso;
	}

	/**
	 * @param aviso the aviso to set
	 */
	public void setAviso(Aviso aviso) {
		this.aviso = aviso;
	}

	/**
	 * @return the resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
     * @return the datosMedioComunicacion
     */
    public MedioComunicacionCliente getDatosMedioComunicacion() {
        return datosMedioComunicacion;
    }

    /**
     * @param datosMedioComunicacion the datosMedioComunicacion to set
     */
    public void setDatosMedioComunicacion(MedioComunicacionCliente datosMedioComunicacion) {
        this.datosMedioComunicacion = datosMedioComunicacion;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(aviso, datosMedioComunicacion, estado, fechaHoraCreacion, fechaHoraEnvio, id, reintentos,
				resultado);
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
		if (!(obj instanceof Notificacion)) {
			return false;
		}
		Notificacion other = (Notificacion) obj;
		return Objects.equals(aviso, other.aviso)
				&& Objects.equals(datosMedioComunicacion, other.datosMedioComunicacion)
				&& Objects.equals(estado, other.estado) && Objects.equals(fechaHoraCreacion, other.fechaHoraCreacion)
				&& Objects.equals(fechaHoraEnvio, other.fechaHoraEnvio) && Objects.equals(id, other.id)
				&& Objects.equals(reintentos, other.reintentos) && Objects.equals(resultado, other.resultado);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notificacion [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (fechaHoraCreacion != null) {
			builder.append("fechaHoraCreacion=");
			builder.append(fechaHoraCreacion);
			builder.append(", ");
		}
		if (fechaHoraEnvio != null) {
			builder.append("fechaHoraEnvio=");
			builder.append(fechaHoraEnvio);
			builder.append(", ");
		}
		if (resultado != null) {
			builder.append("resultado=");
			builder.append(resultado);
			builder.append(", ");
		}
		if (reintentos != null) {
			builder.append("reintentos=");
			builder.append(reintentos);
			builder.append(", ");
		}
		if (estado != null) {
			builder.append("estado=");
			builder.append(estado);
			builder.append(", ");
		}
		if (aviso != null) {
			builder.append("aviso=");
			builder.append(aviso);
			builder.append(", ");
		}
		if (datosMedioComunicacion != null) {
			builder.append("datosMedioComunicacion=");
			builder.append(datosMedioComunicacion);
		}
		builder.append("]");
		return builder.toString();
	}
}
