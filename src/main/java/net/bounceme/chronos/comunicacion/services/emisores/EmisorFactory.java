package net.bounceme.chronos.comunicacion.services.emisores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.model.TipoComunicacion;

/**
 * Clase para la obtención del emisor adecuado según el tipo de comunicación que se use.
 * Se usa el patrón Abstract factory
 * 
 * @author frederik
 *
 */
@Component
public class EmisorFactory {
	
	@Autowired
	private ApplicationContext appContext;
	
	/**
	 * Método de factoría para la obtención del emisor
	 * 
	 * @param tipo
	 * @return el emisor
	 */
	public Emisor getEmisor(TipoComunicacion tipo) {
		return (Emisor) appContext.getBean(tipo.getNombreClaseEmisora());
	}
}
