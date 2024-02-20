package net.bounceme.chronos.comunicacion.services.assemblers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.AvisoDTO;
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.dto.NotificacionDTO;
import net.bounceme.chronos.comunicacion.model.Aviso;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.Notificacion;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("avisoAssembler")
public class AvisoAssembler extends BidirectionalGenericAssembler<Aviso, AvisoDTO> {
	
	@Autowired
	@Qualifier("direccionClienteAssembler")
	private Assembler<DireccionCliente, DireccionClienteDTO> direccionClienteAssembler;
	
	@Autowired
	@Qualifier("notificacionAssembler")
	private Assembler<Notificacion, NotificacionDTO> notificacionAssembler;

    public AvisoAssembler() {
        super(Aviso.class, AvisoDTO.class);
    }

    @Override
    public Aviso reverseAssemble(AvisoDTO target) throws AssembleException {
        return Aviso.builder()
        		.id(target.getId())
        		.fechaInicioObra(target.getFechaInicioObra())
        		.mensaje(target.getMensaje())
        		.estaNotificado(target.getEstaNotificado()).build();
    }

    @Override
    public AvisoDTO assemble(Aviso source) throws AssembleException {
    	List<NotificacionDTO> notificaciones = new ArrayList<>(notificacionAssembler.assemble(source.getNotificaciones()));
    	
    	return AvisoDTO.builder()
    			.id(source.getId())
    			.fechaInicioObra(source.getFechaInicioObra())
    			.mensaje(source.getMensaje())
    			.estaNotificado(source.getEstaNotificado())
    			.direccionCliente(direccionClienteAssembler.assemble(source.getDireccionCliente()))
    			.notificaciones(notificaciones)
    			.build();
    }

}
