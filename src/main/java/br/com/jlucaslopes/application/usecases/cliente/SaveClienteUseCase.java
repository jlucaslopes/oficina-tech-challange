package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.domain.entities.Cliente;

public class SaveClienteUseCase {

    private final ClienteGateway clienteGateway;

    public SaveClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente save(Cliente cliente) {
        return this.clienteGateway.save(cliente);
    }
}
