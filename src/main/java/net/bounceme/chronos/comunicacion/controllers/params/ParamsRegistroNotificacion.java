package net.bounceme.chronos.comunicacion.controllers.params;

import java.util.Date;

public class ParamsRegistroNotificacion {
	
	private Date from;
	private Date to;
	
	/**
	 * @return the from
	 */
	public Date getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(Date from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public Date getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(Date to) {
		this.to = to;
	}
}
