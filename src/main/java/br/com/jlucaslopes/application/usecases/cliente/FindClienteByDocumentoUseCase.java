package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.domain.entities.Cliente;

public class FindClienteByDocumentoUseCase {
    private final ClienteGateway clienteGateway;


    public FindClienteByDocumentoUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente findClienteByDocumento(String documento) {
      return  clienteGateway.findClienteByDocumento(documento);
    }
}
