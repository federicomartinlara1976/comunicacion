package net.bounceme.chronos.comunicacion.controllers.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsDireccion;
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.utils.assemblers.GenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("paramsDireccionAssembler")
public class ParamsDireccionAssembler extends GenericAssembler<ParamsDireccion, DireccionClienteDTO> {

	public ParamsDireccionAssembler() {
		super(ParamsDireccion.class, DireccionClienteDTO.class);
	}

	@Override
	public DireccionClienteDTO assemble(ParamsDireccion source) throws AssembleException {
		DireccionClienteDTO direccionClienteDTO = new DireccionClienteDTO();
		
		direccionClienteDTO.setId(source.getIdDireccion());
		direccionClienteDTO.setDireccion(source.getAddress());
		direccionClienteDTO.setNumero(source.getNumber());
		direccionClienteDTO.setEscalera(source.getEsc());
		direccionClienteDTO.setPiso(source.getFloor());
		direccionClienteDTO.setPuerta(source.getDoor());
		direccionClienteDTO.setLocalidad(source.getCity());
		direccionClienteDTO.setProvincia(source.getProvince());
		direccionClienteDTO.setCodigoPostal(source.getPostalCode());
		
		return direccionClienteDTO;
	}

}
