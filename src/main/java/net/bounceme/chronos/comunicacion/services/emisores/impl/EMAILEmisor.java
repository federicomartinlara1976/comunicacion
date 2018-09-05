package net.bounceme.chronos.comunicacion.services.emisores.impl;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.utils.Constantes.ResultadoEnvio;

/**
 * Emisor para enviar mensajes vía email
 * 
 * @author frederik
 *
 */
@Component("EMAIL_Emisor")
public class EMAILEmisor extends EmisorBase {

    public EMAILEmisor() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public ResultadoEnvio enviar(String mensaje, String numero) {
		// TODO Auto-generated method stub
		return ResultadoEnvio.OK;
	}
}
