package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.domain.entities.Cliente;

public class FindClienteByIdUseCase {
    private final ClienteGateway clienteGateway;

    public FindClienteByIdUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente findClienteById(int id) {
        return clienteGateway.findClienteById(id);
    }
}
