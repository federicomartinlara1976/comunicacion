package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("medioComunicacionClienteAssembler")
public class MedioComunicacionClienteAssembler extends BidirectionalGenericAssembler<MedioComunicacionCliente, MedioComunicacionClienteDTO> {

    @Autowired
    @Qualifier("tipoComunicacionAssembler")
    private BidirectionalAssembler<TipoComunicacion, TipoComunicacionDTO> tipoComunicacionAssembler;
    
    public MedioComunicacionClienteAssembler() {
        super(MedioComunicacionCliente.class, MedioComunicacionClienteDTO.class);
    }

    @Override
    public MedioComunicacionCliente reverseAssemble(MedioComunicacionClienteDTO target) throws AssembleException {
        return MedioComunicacionCliente.builder()
        		.id(target.getId())
        		.valor(target.getValor())
        		.tipoComunicacion(tipoComunicacionAssembler.reverseAssemble(target.getTipoComunicacion()))
        		.build();
    }

    @Override
    public MedioComunicacionClienteDTO assemble(MedioComunicacionCliente source) throws AssembleException {
        return MedioComunicacionClienteDTO.builder()
        		.id(source.getId())
        		.valor(source.getValor())
        		.tipoComunicacion(tipoComunicacionAssembler.assemble(source.getTipoComunicacion()))
        		.build();
    }

    
}
