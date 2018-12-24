package net.bounceme.chronos.comunicacion.utils;

/**
 * Constantes de la aplicacion
 * 
 * @author frederik
 *
 */
public class Constantes {
	public static final String HINT_CACHEABLE = "org.hibernate.cacheable";
	
	public enum ResultadoEnvio {
		OK, FALLO
	}
	
	public enum EstadoNotificacion {
		ENVIADA, NO_ENVIADA, FALLIDA
	}
	
	public static final String TITULO_MENSAJE = "Inicio de obra";
}
