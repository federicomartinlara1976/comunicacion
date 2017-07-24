package net.bounceme.chronos.comunicacion.services.emisores;

import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;

/**
 * Interfaz para los emisores de mensajes. El emisor se seleccionará 
 * en función del tipo de comunicación que se vaya a utilizar
 * 
 * @author frederik
 *
 */
public interface Emisor {
	
	/**
	 * @param mensaje
	 * @param numero
	 * @return
	 */
	ResultadoEnvio enviar(String mensaje, String numero);
}
