package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.dto.RegistroNotificacionDTO;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.comunicacion.model.RegistroNotificacion;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("registroNotificacionAssembler")
public class RegistroNotificacionAssembler extends BidirectionalGenericAssembler<RegistroNotificacion, RegistroNotificacionDTO> {

	@Autowired
	@Qualifier("notificacionAssembler")
	private Assembler<Notificacion, NotificacionDTO> notificacionAssembler;
	
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
        
        registroNotificacionDTO.setNotificacion(notificacionAssembler.assemble(source.getNotificacion()));
        
        return registroNotificacionDTO;
    }

    
}
