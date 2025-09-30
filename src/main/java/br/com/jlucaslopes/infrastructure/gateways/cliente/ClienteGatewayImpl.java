package br.com.jlucaslopes.infrastructure.gateways.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteEntity;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository;
import br.com.jlucaslopes.application.exceptions.ClienteJaExisteException;
import br.com.jlucaslopes.application.exceptions.ClienteNaoEncontradoException;

public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    public ClienteGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findClienteById(int id) {
        return clienteRepository.findById(id)
                .map(ClienteMapper::toCliente)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));
    }

    public Cliente save(Cliente cliente) {
        clienteRepository.findClienteByDocumento(cliente.getDocumento())
                .ifPresent(existingCliente -> {
                    throw new ClienteJaExisteException("Cliente com documento " + cliente.getDocumento() + " ja existe");
                });
        ClienteEntity clientSalvo = clienteRepository.save(ClienteMapper.toEntity(cliente));
        return ClienteMapper.toCliente(clientSalvo);
    }

    public void deleteById(int id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findClienteByDocumento(String documento) {
        return clienteRepository.findClienteByDocumento(documento)
                .map(ClienteMapper::toCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nao encontrado com o documento: " + documento));
    }
}
