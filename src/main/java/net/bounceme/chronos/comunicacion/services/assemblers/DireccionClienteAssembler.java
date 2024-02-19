package net.bounceme.chronos.comunicacion.services.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.comunicacion.data.model.Cliente;
import net.bounceme.chronos.comunicacion.data.model.DireccionCliente;
import net.bounceme.chronos.comunicacion.dto.ClienteDTO;
import net.bounceme.chronos.comunicacion.dto.DireccionClienteDTO;
import net.bounceme.chronos.utils.assemblers.BidirectionalAssembler;
import net.bounceme.chronos.utils.assemblers.BidirectionalGenericAssembler;
import net.bounceme.chronos.utils.exceptions.AssembleException;

@Component("direccionClienteAssembler")
public class DireccionClienteAssembler extends BidirectionalGenericAssembler<DireccionCliente, DireccionClienteDTO> {
	
	@Autowired
	@Qualifier("clienteAssembler")
	private BidirectionalAssembler<Cliente, ClienteDTO> clienteAssembler;
	
    public DireccionClienteAssembler() {
        super(DireccionCliente.class, DireccionClienteDTO.class);
    }

    @Override
    public DireccionCliente reverseAssemble(DireccionClienteDTO target) throws AssembleException {
        return DireccionCliente.builder()
        		.id(target.getId())
        		.direccion(target.getDireccion())
        		.numero(target.getNumero())
        		.escalera(target.getEscalera())
        		.piso(target.getPiso())
        		.puerta(target.getPuerta())
        		.codigoPostal(target.getCodigoPostal())
        		.localidad(target.getLocalidad())
        		.provincia(target.getProvincia())
        		.cliente(clienteAssembler.reverseAssemble(target.getCliente()))
        		.build(); 
    }

    @Override
    public DireccionClienteDTO assemble(DireccionCliente source) throws AssembleException {
    	return DireccionClienteDTO.builder()
        		.id(source.getId())
        		.direccion(source.getDireccion())
        		.numero(source.getNumero())
        		.escalera(source.getEscalera())
        		.piso(source.getPiso())
        		.puerta(source.getPuerta())
        		.codigoPostal(source.getCodigoPostal())
        		.localidad(source.getLocalidad())
        		.provincia(source.getProvincia())
        		.cliente(clienteAssembler.assemble(source.getCliente()))
        		.build();
    }

    
}
