package net.bounceme.chronos.comunicacion.services.emisores.impl;

import javax.mail.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.utils.Constantes;
import net.bounceme.chronos.comunicacion.utils.EmailUtil;
import net.bounceme.chronos.comunicacion.config.AppConfig;
import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;

/**
 * Emisor para enviar mensajes vía email
 * 
 * @author frederik
 *
 */
@Component("EMAIL_Emisor")
public class EMAILEmisor extends EmisorBase {
	
	private static final Logger log = Logger.getLogger(EMAILEmisor.class);
	
	@Autowired
	@Qualifier(AppConfig.TLS_MAIL_SESSION)
	private Session session;

    public EMAILEmisor() {}

	@Override
	public ResultadoEnvio enviar(String mensaje, String numero) {
		try {
			EmailUtil.sendEmail(session, numero, Constantes.TITULO_MENSAJE , mensaje);
			
			return ResultadoEnvio.OK;
		} catch (Exception e) {
			log.error(e);
			return ResultadoEnvio.FALLO;
		}
	}
}
