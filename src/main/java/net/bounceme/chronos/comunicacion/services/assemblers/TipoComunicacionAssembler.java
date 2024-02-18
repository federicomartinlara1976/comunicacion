package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.TipoComunicacionDTO;
import net.bounceme.chronos.comunicacion.model.TipoComunicacion;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("tipoComunicacionAssembler")
public class TipoComunicacionAssembler extends BidirectionalGenericAssembler<TipoComunicacion, TipoComunicacionDTO>{

    public TipoComunicacionAssembler() {
        super(TipoComunicacion.class, TipoComunicacionDTO.class);
    }

    @Override
    public TipoComunicacion reverseAssemble(TipoComunicacionDTO target) throws AssembleException {
        return TipoComunicacion.builder()
        		.id(target.getId())
        		.denominacion(target.getDenominacion())
        		.nombreClaseEmisora(target.getNombreClaseEmisora())
        		.icono(target.getIcono())
        		.build();
    }

    @Override
    public TipoComunicacionDTO assemble(TipoComunicacion source) throws AssembleException {
    	return TipoComunicacionDTO.builder()
        		.id(source.getId())
        		.denominacion(source.getDenominacion())
        		.nombreClaseEmisora(source.getNombreClaseEmisora())
        		.icono(source.getIcono())
        		.build();
    }
}
