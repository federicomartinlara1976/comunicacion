package net.bounceme.chronos.comunicacion.exceptions;

/**
 * Error para la capa de presentación
 * 
 * @author frederik
 *
 */
public class ControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5837841883202047609L;

	public ControllerException() {}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
