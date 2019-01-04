package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("medioComunicacionClienteAssembler")
public class MedioComunicacionClienteAssembler extends BidirectionalGenericAssembler<MedioComunicacionCliente, MedioComunicacionClienteDTO> {

    public MedioComunicacionClienteAssembler() {
        super(MedioComunicacionCliente.class, MedioComunicacionClienteDTO.class);
    }

    @Override
    public MedioComunicacionCliente reverseAssemble(MedioComunicacionClienteDTO target) throws AssembleException {
        MedioComunicacionCliente medioComunicacionCliente = new MedioComunicacionCliente();
        
        medioComunicacionCliente.setId(target.getId());
        medioComunicacionCliente.setValor(target.getValor());
        
        return medioComunicacionCliente;
    }

    @Override
    public MedioComunicacionClienteDTO assemble(MedioComunicacionCliente source) throws AssembleException {
        MedioComunicacionClienteDTO medioComunicacionClienteDTO = new MedioComunicacionClienteDTO();
        
        medioComunicacionClienteDTO.setId(source.getId());
        medioComunicacionClienteDTO.setValor(source.getValor());
        
        return medioComunicacionClienteDTO;
    }

    
}
