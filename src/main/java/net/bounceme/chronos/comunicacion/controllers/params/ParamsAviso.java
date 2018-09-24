package net.bounceme.chronos.comunicacion.controllers.params;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class ParamsAviso {

	@NotBlank
    private Long idCliente;
    
	@NotBlank
    private Long idDireccion;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaInicioObra;
    
    @NotBlank
    private String mensaje;

    /**
     * @return the idCliente
     */
    public Long getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    /**
	 * @return the idDireccion
	 */
	public Long getIdDireccion() {
		return idDireccion;
	}

	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
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
}
