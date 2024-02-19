package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.data.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.data.model.Notificacion;
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("notificacionAssembler")
public class NotificacionAssembler extends BidirectionalGenericAssembler<Notificacion, NotificacionDTO> {
	
	@Autowired
	@Qualifier("medioComunicacionClienteAssembler")
	private BidirectionalAssembler<MedioComunicacionCliente, MedioComunicacionClienteDTO> medioComunicacionClienteAssembler;

    public NotificacionAssembler() {
        super(Notificacion.class, NotificacionDTO.class);
    }

    @Override
    public Notificacion reverseAssemble(NotificacionDTO target) throws AssembleException {
        return Notificacion.builder()
        		.id(target.getId())
        		.estado(target.getEstado())
        		.fechaHoraCreacion(target.getFechaHoraCreacion())
        		.fechaHoraEnvio(target.getFechaHoraEnvio())
        		.reintentos(target.getReintentos())
        		.resultado(target.getResultado())
        		.medioComunicacionCliente(medioComunicacionClienteAssembler.reverseAssemble(target.getMedioComunicacion()))
        		.build();
    }

    @Override
    public NotificacionDTO assemble(Notificacion source) throws AssembleException {
        return NotificacionDTO.builder()
        		.id(source.getId())
        		.estado(source.getEstado())
        		.fechaHoraCreacion(source.getFechaHoraCreacion())
        		.fechaHoraEnvio(source.getFechaHoraEnvio())
        		.reintentos(source.getReintentos())
        		.resultado(source.getResultado())
        		.medioComunicacion(medioComunicacionClienteAssembler.assemble(source.getMedioComunicacionCliente()))
        		.build();
    }
}
