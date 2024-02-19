package net.bounceme.chronos.comunicacion.services.assemblers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.data.model.Aviso;
import net.bounceme.chronos.comunicacion.data.model.Cliente;
import net.bounceme.chronos.comunicacion.data.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.data.model.MedioComunicacionCliente;
import net.bounceme.chronos.comunicacion.dto.AvisoDTO;
import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.utils.assemblers.Assembler;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("clienteAssembler")
public class ClienteAssembler extends BidirectionalGenericAssembler<Cliente, ClienteDTO> {
	
	@Autowired
	@Qualifier("medioComunicacionClienteAssembler")
	private Assembler<MedioComunicacionCliente, MedioComunicacionClienteDTO> mediosComunicacionAssembler;
	
	@Autowired
	@Qualifier("direccionClienteAssembler")
	private Assembler<DireccionCliente, DireccionClienteDTO> direccionesClienteAssembler;
	
	@Autowired
	@Qualifier("avisoAssembler")
	private Assembler<Aviso, AvisoDTO> avisoAssembler;

    public ClienteAssembler() {
        super(Cliente.class, ClienteDTO.class);
    }

    @Override
    public Cliente reverseAssemble(ClienteDTO target) throws AssembleException {
        return Cliente.builder()
        		.id(target.getId())
        		.nombre(target.getNombre())
        		.apellidos(target.getApellidos())
        		.dni(target.getDni())
        		.build();
    }

    @Override
    public ClienteDTO assemble(Cliente source) throws AssembleException {
    	List<MedioComunicacionClienteDTO> listMediosComunicacion = new ArrayList<>(mediosComunicacionAssembler.assemble(source.getMediosComunicacion()));
        List<DireccionClienteDTO> listDirecciones = new ArrayList<>(direccionesClienteAssembler.assemble(source.getDirecciones()));
        List<AvisoDTO> listAvisos = new ArrayList<>(avisoAssembler.assemble(source.getAvisos()));
    	
    	return ClienteDTO.builder()
    			.id(source.getId())
        		.nombre(source.getNombre())
        		.apellidos(source.getApellidos())
        		.dni(source.getDni())
        		.mediosComunicacion(listMediosComunicacion)
        		.direcciones(listDirecciones)
        		.avisos(listAvisos)
        		.build();
    }

}
