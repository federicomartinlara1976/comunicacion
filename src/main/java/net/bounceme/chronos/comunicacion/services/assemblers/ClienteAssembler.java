package net.bounceme.chronos.comunicacion.services.assemblers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.comunicacion.dto.MedioComunicacionClienteDTO;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.comunicacion.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.model.MedioComunicacionCliente;
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

    public ClienteAssembler() {
        super(Cliente.class, ClienteDTO.class);
    }

    @Override
    public Cliente reverseAssemble(ClienteDTO target) throws AssembleException {
        Cliente cliente = new Cliente();
        
        cliente.setId(target.getId());
        cliente.setNombre(target.getNombre());
        cliente.setApellidos(target.getApellidos());
        cliente.setDni(target.getDni());
        
        return cliente;
    }

    @Override
    public ClienteDTO assemble(Cliente source) throws AssembleException {
        ClienteDTO clienteDTO = new ClienteDTO();
        
        clienteDTO.setId(source.getId());
        clienteDTO.setNombre(source.getNombre());
        clienteDTO.setApellidos(source.getApellidos());
        clienteDTO.setDni(source.getDni());
        
        List<MedioComunicacionClienteDTO> listMediosComunicacion = new ArrayList<>(mediosComunicacionAssembler.assemble(source.getMediosComunicacion()));
        List<DireccionClienteDTO> listDirecciones = new ArrayList<>(direccionesClienteAssembler.assemble(source.getDirecciones()));
        
        clienteDTO.setMediosComunicacion(listMediosComunicacion);
        clienteDTO.setDirecciones(listDirecciones);
        
        return clienteDTO;
    }

}
