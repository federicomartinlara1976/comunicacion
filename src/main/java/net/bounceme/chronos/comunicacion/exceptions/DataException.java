package net.bounceme.chronos.comunicacion.exceptions;

/**
 * Error para la capa de repositorios
 * 
 * @author frederik
 *
 */
public class DataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataException() {}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
