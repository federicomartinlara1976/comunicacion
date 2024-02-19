package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.data.model.Notificacion;
import net.bounceme.chronos.comunicacion.data.model.RegistroNotificacion;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.dto.RegistroNotificacionDTO;
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
        return RegistroNotificacion.builder()
        		.id(target.getId())
        		.fechaHoraNotificacion(target.getFechaHoraNotificacion())
        		.resultado(target.getResultado())
        		.build();
    }

    @Override
    public RegistroNotificacionDTO assemble(RegistroNotificacion source) throws AssembleException {
        return RegistroNotificacionDTO.builder()
        		.id(source.getId())
        		.fechaHoraNotificacion(source.getFechaHoraNotificacion())
        		.resultado(source.getResultado())
        		.notificacion(notificacionAssembler.assemble(source.getNotificacion()))
        		.build();
    }
}
