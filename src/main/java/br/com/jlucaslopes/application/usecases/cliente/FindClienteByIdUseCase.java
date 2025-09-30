package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;

public class FindClienteByIdUseCase {
    private final ClienteGateway clienteGateway;

    public FindClienteByIdUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void findUserById(int id) {
        clienteGateway.findClienteById(id);
    }
}
