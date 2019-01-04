package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.model.Cliente;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("clienteAssembler")
public class ClienteAssembler extends BidirectionalGenericAssembler<Cliente, ClienteDTO> {

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
        
        return clienteDTO;
    }

}
