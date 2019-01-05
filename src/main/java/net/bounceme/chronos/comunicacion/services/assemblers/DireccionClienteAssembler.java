package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.helpers.DireccionHelper;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("direccionClienteAssembler")
public class DireccionClienteAssembler extends BidirectionalGenericAssembler<DireccionCliente, DireccionClienteDTO> {

	@Autowired
	private DireccionHelper direccionClienteHelper; 
	
    public DireccionClienteAssembler() {
        super(DireccionCliente.class, DireccionClienteDTO.class);
    }

    @Override
    public DireccionCliente reverseAssemble(DireccionClienteDTO target) throws AssembleException {
        DireccionCliente direccionCliente = new DireccionCliente();
        
        direccionCliente.setId(target.getId());
        direccionClienteHelper.copyDireccion(target, direccionCliente);
        
        return direccionCliente;
    }

    @Override
    public DireccionClienteDTO assemble(DireccionCliente source) throws AssembleException {
        DireccionClienteDTO direccionClienteDTO = new DireccionClienteDTO();
        
        direccionClienteDTO.setId(source.getId());
        direccionClienteHelper.copyDireccion(source, direccionClienteDTO);
        
        return direccionClienteDTO;
    }

    
}
