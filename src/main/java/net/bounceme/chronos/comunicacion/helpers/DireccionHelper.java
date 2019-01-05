package net.bounceme.chronos.comunicacion.helpers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.common.Direccion;

@Component
public class DireccionHelper {

	
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
