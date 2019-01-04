package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.AvisoDTO;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("avisoAssembler")
public class AvisoAssembler extends BidirectionalGenericAssembler<Aviso, AvisoDTO> {

    public AvisoAssembler() {
        super(Aviso.class, AvisoDTO.class);
    }

    @Override
    public Aviso reverseAssemble(AvisoDTO target) throws AssembleException {
        Aviso aviso = new Aviso();
        
        aviso.setId(target.getId());
        aviso.setFechaInicioObra(target.getFechaInicioObra());
        aviso.setMensaje(target.getMensaje());
        aviso.setEstaNotificado(target.getEstaNotificado());
        
        return aviso;
    }

    @Override
    public AvisoDTO assemble(Aviso source) throws AssembleException {
        AvisoDTO avisoDTO = new AvisoDTO();
        
        avisoDTO.setId(source.getId());
        avisoDTO.setFechaInicioObra(source.getFechaInicioObra());
        avisoDTO.setMensaje(source.getMensaje());
        avisoDTO.setEstaNotificado(source.getEstaNotificado());
        
        return avisoDTO;
    }

}
