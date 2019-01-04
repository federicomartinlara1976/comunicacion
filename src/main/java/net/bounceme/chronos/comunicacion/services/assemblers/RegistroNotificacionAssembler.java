package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.RegistroNotificacionDTO;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("registroNotificacionAssembler")
public class RegistroNotificacionAssembler extends BidirectionalGenericAssembler<RegistroNotificacion, RegistroNotificacionDTO> {

    public RegistroNotificacionAssembler() {
        super(RegistroNotificacion.class, RegistroNotificacionDTO.class);
    }

    @Override
    public RegistroNotificacion reverseAssemble(RegistroNotificacionDTO target) throws AssembleException {
        RegistroNotificacion registroNotificacion = new RegistroNotificacion();
        
        registroNotificacion.setId(target.getId());
        registroNotificacion.setFechaHoraNotificacion(target.getFechaHoraNotificacion());
        registroNotificacion.setResultado(target.getResultado());
        
        return registroNotificacion;
    }

    @Override
    public RegistroNotificacionDTO assemble(RegistroNotificacion source) throws AssembleException {
        RegistroNotificacionDTO registroNotificacionDTO = new RegistroNotificacionDTO();
        
        registroNotificacionDTO.setId(source.getId());
        registroNotificacionDTO.setFechaHoraNotificacion(source.getFechaHoraNotificacion());
        registroNotificacionDTO.setResultado(source.getResultado());
        
        return registroNotificacionDTO;
    }

    
}
