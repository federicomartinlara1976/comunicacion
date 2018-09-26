package net.bounceme.chronos.comunicacion.services.emisores.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.services.emisores.Emisor;
import net.bounceme.chronos.comunicacion.services.helpers.EmailHelper;
import net.bounceme.chronos.comunicacion.utils.Constantes;
import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;

/**
 * Emisor para enviar mensajes vía email
 * 
 * @author frederik
 *
 */
@Component("EMAIL_Emisor")
public class EMAILEmisor implements Emisor {
	
	private static final Logger log = Logger.getLogger(EMAILEmisor.class);

	@Autowired
	private EmailHelper helper;
	
    /**
     * 
     */
    public EMAILEmisor() {}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.emisores.Emisor#enviar(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultadoEnvio enviar(String mensaje, String numero) {
		try {
			helper.sendEmail(numero, Constantes.TITULO_MENSAJE , mensaje);
			
			return ResultadoEnvio.OK;
		} catch (Exception e) {
			log.error(e);
			return ResultadoEnvio.FALLO;
		}
	}
}
