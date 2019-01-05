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
        TipoComunicacion tipoComunicacion = new TipoComunicacion();
        
        tipoComunicacion.setId(target.getId());
        tipoComunicacion.setDenominacion(target.getDenominacion());
        tipoComunicacion.setNombreClaseEmisora(target.getNombreClaseEmisora());
        tipoComunicacion.setIcono(target.getIcono());
        
        return tipoComunicacion;
    }

    @Override
    public TipoComunicacionDTO assemble(TipoComunicacion source) throws AssembleException {
        TipoComunicacionDTO tipoComunicacionDTO = new TipoComunicacionDTO();
        
        tipoComunicacionDTO.setId(source.getId());
        tipoComunicacionDTO.setDenominacion(source.getDenominacion());
        tipoComunicacionDTO.setNombreClaseEmisora(source.getNombreClaseEmisora());
        tipoComunicacionDTO.setIcono(source.getIcono());
        
        return tipoComunicacionDTO;
    }

}
