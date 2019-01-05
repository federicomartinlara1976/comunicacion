package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("notificacionAssembler")
public class NotificacionAssembler extends BidirectionalGenericAssembler<Notificacion, NotificacionDTO> {

    public NotificacionAssembler() {
        super(Notificacion.class, NotificacionDTO.class);
    }

    @Override
    public Notificacion reverseAssemble(NotificacionDTO target) throws AssembleException {
        Notificacion notificacion = new Notificacion();
        
        notificacion.setId(target.getId());
        notificacion.setEstado(target.getEstado());
        notificacion.setFechaHoraCreacion(target.getFechaHoraCreacion());
        notificacion.setFechaHoraEnvio(target.getFechaHoraEnvio());
        notificacion.setReintentos(target.getReintentos());
        notificacion.setResultado(target.getResultado());
        
        return notificacion;
    }

    @Override
    public NotificacionDTO assemble(Notificacion source) throws AssembleException {
        NotificacionDTO notificacionDTO = new NotificacionDTO();
        
        notificacionDTO.setId(source.getId());
        notificacionDTO.setEstado(source.getEstado());
        notificacionDTO.setFechaHoraCreacion(source.getFechaHoraCreacion());
        notificacionDTO.setFechaHoraEnvio(source.getFechaHoraEnvio());
        notificacionDTO.setReintentos(source.getReintentos());
        notificacionDTO.setResultado(source.getResultado());
        
        return notificacionDTO;
    }
    
    
}
