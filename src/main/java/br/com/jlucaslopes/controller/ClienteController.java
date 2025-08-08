package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operações relacionadas a gestao de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados do cliente correspondente ao ID informado.")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(clienteService.findClienteById(Integer.parseInt(id)));
    }

    @GetMapping("documento/{documento}")
    @Operation(summary = "Buscar cliente por documento", description = "Retorna os dados do cliente correspondente ao documento informado.")
    public ResponseEntity<Cliente> getClienteByDocumento(@PathVariable("documento") String documento) {
        return ResponseEntity.ok(clienteService.findClienteByDocumento(documento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por ID", description = "Remove o cliente correspondente ao ID informado.")
    public ResponseEntity<Void> deleteClienteById(@PathVariable("id") String id) {
        clienteService.deleteById(Integer.parseInt(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    @Operation(summary = "Criar novo cliente", description = "Cadastra um novo cliente no sistema.")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }
}
