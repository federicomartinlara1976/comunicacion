package net.bounceme.chronos.comunicacion.model;

import java.io.Serializable;

public class RegistroNotificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323776740630772201L;

	private Notificacion notificacion;

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}
}
