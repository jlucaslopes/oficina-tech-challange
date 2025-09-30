package br.com.jlucaslopes.application.gateways;

import br.com.jlucaslopes.domain.entities.Cliente;

public interface ClienteGateway {

     Cliente findClienteById(int id);

     Cliente save(Cliente cliente);

     void deleteById(int id);

     Cliente findClienteByDocumento(String documento);
}
