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
        
        avisoDTO.setDireccionCliente(direccionClienteAssembler.assemble(source.getDireccionCliente()));
        
        List<NotificacionDTO> notificaciones = new ArrayList<>(notificacionAssembler.assemble(source.getNotificaciones()));
        avisoDTO.setNotificaciones(notificaciones);
        
        return avisoDTO;
    }

}
