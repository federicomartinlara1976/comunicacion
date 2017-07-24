package net.bounceme.chronos.comunicacion.controllers.params;

/**
 * @author frederik
 *
 */
public class ParamsNotificacion {

    private Long idAviso;
    
    private Long idTipoMedio;
    
    private Long idNotificacion;

	/**
	 * @return the idAviso
	 */
	public Long getIdAviso() {
		return idAviso;
	}

	/**
	 * @param idAviso the idAviso to set
	 */
	public void setIdAviso(Long idAviso) {
		this.idAviso = idAviso;
	}

	/**
	 * @return the idTipoMedio
	 */
	public Long getIdTipoMedio() {
		return idTipoMedio;
	}

	/**
	 * @param idTipoMedio the idTipoMedio to set
	 */
	public void setIdTipoMedio(Long idTipoMedio) {
		this.idTipoMedio = idTipoMedio;
	}

	/**
	 * @return the idNotificacion
	 */
	public Long getIdNotificacion() {
		return idNotificacion;
	}

	/**
	 * @param idNotificacion the idNotificacion to set
	 */
	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
}
