package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("direccionClienteAssembler")
public class DireccionClienteAssembler extends BidirectionalGenericAssembler<DireccionCliente, DireccionClienteDTO> {

    public DireccionClienteAssembler() {
        super(DireccionCliente.class, DireccionClienteDTO.class);
    }

    @Override
    public DireccionCliente reverseAssemble(DireccionClienteDTO target) throws AssembleException {
        DireccionCliente direccionCliente = new DireccionCliente();
        
        direccionCliente.setId(target.getId());
        direccionCliente.setDireccion(target.getDireccion());
        direccionCliente.setNumero(target.getNumero());
        direccionCliente.setEscalera(target.getEscalera());
        direccionCliente.setPiso(target.getPiso());
        direccionCliente.setPuerta(target.getPuerta());
        direccionCliente.setLocalidad(target.getLocalidad());
        direccionCliente.setProvincia(target.getProvincia());
        direccionCliente.setCodigoPostal(target.getCodigoPostal());
        
        return direccionCliente;
    }

    @Override
    public DireccionClienteDTO assemble(DireccionCliente source) throws AssembleException {
        DireccionClienteDTO direccionClienteDTO = new DireccionClienteDTO();
        
        direccionClienteDTO.setId(source.getId());
        direccionClienteDTO.setDireccion(source.getDireccion());
        direccionClienteDTO.setNumero(source.getNumero());
        direccionClienteDTO.setEscalera(source.getEscalera());
        direccionClienteDTO.setPiso(source.getPiso());
        direccionClienteDTO.setPuerta(source.getPuerta());
        direccionClienteDTO.setLocalidad(source.getLocalidad());
        direccionClienteDTO.setProvincia(source.getProvincia());
        direccionClienteDTO.setCodigoPostal(source.getCodigoPostal());
        
        return direccionClienteDTO;
    }

    
}
