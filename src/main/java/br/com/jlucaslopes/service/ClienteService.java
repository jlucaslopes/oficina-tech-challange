package br.com.jlucaslopes.service;


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
        return clienteRepository.save(cliente);
    }
    public void deleteById(int id) {
        clienteRepository.deleteById(id);
    }
}
