package net.bounceme.chronos.comunicacion.services.emisores.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;

/**
 * Emisor para envío de mensajes vía SMS
 * 
 * @author frederik
 *
 */
@Component("SMS_Emisor")
public class SMSEmisor extends EmisorBase {

	private static final Logger log = Logger.getLogger(SMSEmisor.class);
	
	private static final String URL = "http://localhost:9100/tinsa/sms";
	
	/**
	 * 
	 */
	public SMSEmisor() {
		super();
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.emisores.Emisor#enviar(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultadoEnvio enviar(String mensaje, String numero) {
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("phone", numero);
			parameters.put("message", mensaje.replace("\\s+", "_"));
			
			String result = get(URL, parameters);
			log.info(result);
			
			return ResultadoEnvio.OK;
		} catch (Exception e) {
			log.error(e);
			return ResultadoEnvio.FALLO;
		}
	}
}
