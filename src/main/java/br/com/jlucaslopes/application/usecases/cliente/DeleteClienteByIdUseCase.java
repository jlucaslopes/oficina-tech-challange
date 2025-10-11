package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;

public class DeleteClienteByIdUseCase {

    private final ClienteGateway clienteGateway;

    public DeleteClienteByIdUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void deleteById(int id) {
        clienteGateway.deleteById(id);
    }
}
