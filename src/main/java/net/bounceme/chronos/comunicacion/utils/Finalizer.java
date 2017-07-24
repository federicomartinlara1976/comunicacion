package net.bounceme.chronos.comunicacion.utils;

import org.apache.log4j.Logger;

public abstract class Finalizer {

	private static final Logger log = Logger.getLogger(Finalizer.class);
	
	public Finalizer() {
		// TODO Auto-generated constructor stub
	}

	protected void finalize() {
		log.info("Finalize");
	}
}
