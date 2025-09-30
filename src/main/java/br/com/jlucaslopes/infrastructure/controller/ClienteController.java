package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operações relacionadas a gestao de clientes")
public class ClienteController {

    private final ClienteGateway clienteGateway;

    public ClienteController(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados do cliente correspondente ao ID informado.")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(clienteGateway.findClienteById(Integer.parseInt(id)));
    }

    @GetMapping("documento/{documento}")
    @Operation(summary = "Buscar cliente por documento", description = "Retorna os dados do cliente correspondente ao documento informado.")
    public ResponseEntity<Cliente> getClienteByDocumento(@PathVariable("documento") String documento) {
        return ResponseEntity.ok(clienteGateway.findClienteByDocumento(documento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por ID", description = "Remove o cliente correspondente ao ID informado.")
    public ResponseEntity<Void> deleteClienteById(@PathVariable("id") String id) {
        clienteGateway.deleteById(Integer.parseInt(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    @Operation(summary = "Criar novo cliente", description = "Cadastra um novo cliente no sistema.")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteGateway.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }
}
