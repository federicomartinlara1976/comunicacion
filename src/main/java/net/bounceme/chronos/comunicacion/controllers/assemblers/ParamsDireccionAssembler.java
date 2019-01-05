package net.bounceme.chronos.comunicacion.controllers.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.controllers.params.ParamsDireccion;
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.helpers.DireccionHelper;
import net.bounceme.chronos.utils.assemblers.GenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("paramsDireccionAssembler")
public class ParamsDireccionAssembler extends GenericAssembler<ParamsDireccion, DireccionClienteDTO> {

	@Autowired
	private DireccionHelper direccionClienteHelper; 
	
	public ParamsDireccionAssembler() {
		super(ParamsDireccion.class, DireccionClienteDTO.class);
	}

	@Override
	public DireccionClienteDTO assemble(ParamsDireccion source) throws AssembleException {
		DireccionClienteDTO direccionClienteDTO = new DireccionClienteDTO();
		
		direccionClienteDTO.setId(source.getIdDireccion());
		direccionClienteHelper.copyDireccion(source, direccionClienteDTO);
		
		return direccionClienteDTO;
	}

}
