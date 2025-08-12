package br.com.jlucaslopes.service;


import br.com.jlucaslopes.model.exception.ClienteJaExisteException;
import br.com.jlucaslopes.model.exception.ClienteNaoEncontradoException;
import br.com.jlucaslopes.repository.ClienteRepository;
import br.com.jlucaslopes.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findClienteById(int id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente nao encontrado")); // Placeholder return statement
    }

    public Cliente save(Cliente cliente) {
        clienteRepository.findClienteByDocumento(cliente.getDocumento())
                .ifPresent(existingCliente -> {
                    throw new ClienteJaExisteException("Cliente com documento " + cliente.getDocumento() + " ja existe");
                });
        return clienteRepository.save(cliente);
    }
    public void deleteById(int id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findClienteByDocumento(String documento) {
        return clienteRepository.findClienteByDocumento(documento)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nao encontrado com o documento: " + documento));
    }

}
