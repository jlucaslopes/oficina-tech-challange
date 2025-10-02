package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.application.usecases.cliente.DeleteClienteByIdUseCase;
import br.com.jlucaslopes.application.usecases.cliente.FindClienteByDocumentoUseCase;
import br.com.jlucaslopes.application.usecases.cliente.FindClienteByIdUseCase;
import br.com.jlucaslopes.application.usecases.cliente.SaveClienteUseCase;
import br.com.jlucaslopes.domain.entities.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operações relacionadas a gestao de clientes")
public class ClienteController {

    private final DeleteClienteByIdUseCase deleteClienteByIdUseCase;
    private final FindClienteByDocumentoUseCase findClienteByDocumentoUseCase;
    private final FindClienteByIdUseCase findClienteByIdUseCase;
    private final SaveClienteUseCase saveClienteUseCase;

    public ClienteController(DeleteClienteByIdUseCase deleteClienteByIdUseCase,
                             FindClienteByDocumentoUseCase findClienteByDocumentoUseCase,
                             FindClienteByIdUseCase findClienteByIdUseCase,
                             SaveClienteUseCase saveClienteUseCase) {
        this.deleteClienteByIdUseCase = deleteClienteByIdUseCase;
        this.findClienteByDocumentoUseCase = findClienteByDocumentoUseCase;
        this.findClienteByIdUseCase = findClienteByIdUseCase;
        this.saveClienteUseCase = saveClienteUseCase;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados do cliente correspondente ao ID informado.")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(findClienteByIdUseCase.findClienteById(Integer.parseInt(id)));
    }

    @GetMapping("documento/{documento}")
    @Operation(summary = "Buscar cliente por documento", description = "Retorna os dados do cliente correspondente ao documento informado.")
    public ResponseEntity<Cliente> getClienteByDocumento(@PathVariable("documento") String documento) {
        return ResponseEntity.ok(findClienteByDocumentoUseCase.findClienteByDocumento(documento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por ID", description = "Remove o cliente correspondente ao ID informado.")
    public ResponseEntity<Void> deleteClienteById(@PathVariable("id") String id) {
        deleteClienteByIdUseCase.deleteById(Integer.parseInt(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    @Operation(summary = "Criar novo cliente", description = "Cadastra um novo cliente no sistema.")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = saveClienteUseCase.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }
}
