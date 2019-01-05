package net.bounceme.chronos.comunicacion.services.helpers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.common.Direccion;

@Component
public class DireccionClienteHelper {

	
	/**
	 * @param source
	 * @param target
	 */
	public void copyDireccion(Direccion source, Direccion target) {
		target.setDireccion(source.getDireccion());
		target.setNumero(source.getNumero());
		target.setEscalera(source.getEscalera());
		target.setPiso(source.getPiso());
		target.setPuerta(source.getPuerta());
		target.setLocalidad(source.getLocalidad());
		target.setProvincia(source.getProvincia());
		target.setCodigoPostal(source.getCodigoPostal());
	}
}
