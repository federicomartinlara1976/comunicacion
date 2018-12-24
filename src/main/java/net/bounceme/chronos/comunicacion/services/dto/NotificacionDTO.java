package net.bounceme.chronos.comunicacion.services.dto;

import java.io.Serializable;
import java.util.Date;

public class NotificacionDTO implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = -8123947878791673294L;

    private Long id;
	
	private Date fechaHoraCreacion;
	
	private Date fechaHoraEnvio;
	
	private String resultado;
	
	private Integer reintentos;
	
	private String estado;
	
	private AvisoDTO aviso;
	
	private MedioComunicacionClienteDTO datosMedioComunicacion;

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
	public AvisoDTO getAviso() {
		return aviso;
	}

	/**
	 * @param aviso the aviso to set
	 */
	public void setAviso(AvisoDTO aviso) {
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
    public MedioComunicacionClienteDTO getDatosMedioComunicacion() {
        return datosMedioComunicacion;
    }

    /**
     * @param datosMedioComunicacion the datosMedioComunicacion to set
     */
    public void setDatosMedioComunicacion(MedioComunicacionClienteDTO datosMedioComunicacion) {
        this.datosMedioComunicacion = datosMedioComunicacion;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Notificacion [id=" + id + ", fechaHoraCreacion=" + fechaHoraCreacion
            + ", fechaHoraEnvio=" + fechaHoraEnvio + ", resultado=" + resultado + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aviso == null) ? 0 : aviso.hashCode());
        result = prime * result
            + ((datosMedioComunicacion == null) ? 0 : datosMedioComunicacion.hashCode());
        result = prime * result + ((fechaHoraCreacion == null) ? 0 : fechaHoraCreacion.hashCode());
        result = prime * result + ((fechaHoraEnvio == null) ? 0 : fechaHoraEnvio.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((resultado == null) ? 0 : resultado.hashCode());
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
        NotificacionDTO other = (NotificacionDTO) obj;
        if (aviso == null) {
            if (other.aviso != null)
                return false;
        }
        else if (!aviso.equals(other.aviso))
            return false;
        if (datosMedioComunicacion == null) {
            if (other.datosMedioComunicacion != null)
                return false;
        }
        else if (!datosMedioComunicacion.equals(other.datosMedioComunicacion))
            return false;
        if (fechaHoraCreacion == null) {
            if (other.fechaHoraCreacion != null)
                return false;
        }
        else if (!fechaHoraCreacion.equals(other.fechaHoraCreacion))
            return false;
        if (fechaHoraEnvio == null) {
            if (other.fechaHoraEnvio != null)
                return false;
        }
        else if (!fechaHoraEnvio.equals(other.fechaHoraEnvio))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (resultado == null) {
            if (other.resultado != null)
                return false;
        }
        else if (!resultado.equals(other.resultado))
            return false;
        return true;
    }
}
