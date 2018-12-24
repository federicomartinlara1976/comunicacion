package net.bounceme.chronos.comunicacion.services.emisores.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.services.emisores.Emisor;
import net.bounceme.chronos.comunicacion.services.helpers.HttpHelper;
import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;

/**
 * Emisor para envío de mensajes vía SMS
 * 
 * @author frederik
 *
 */
@Component("SMS_Emisor")
public class SMSEmisor implements Emisor {

	private static final Logger log = LoggerFactory.getLogger(SMSEmisor.class);
	
	private static final String URL = "http://localhost:9100/tinsa/sms";
	
	@Autowired
	private HttpHelper helper;
	
	/* (non-Javadoc)
	 * @see net.bounceme.chronos.comunicacion.services.emisores.Emisor#enviar(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultadoEnvio enviar(String mensaje, String numero) {
		try {
			Map<String, String> parameters = new HashMap<>();
			parameters.put("phone", numero);
			parameters.put("message", mensaje.replace("\\s+", "_"));
			
			String result = helper.get(URL, parameters);
			log.info(result);
			
			return ResultadoEnvio.OK;
		} catch (Exception e) {
			log.error("ERROR: ", e);
			return ResultadoEnvio.FALLO;
		}
	}
}
