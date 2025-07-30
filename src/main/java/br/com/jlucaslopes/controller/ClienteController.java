package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente" )
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") String id) {
        return ResponseEntity.ok(clienteService.findClienteById(Integer.parseInt(id)));
    }

    @GetMapping("documento/{documento}")
    public ResponseEntity<Cliente> getClienteByDocumento(@PathVariable("documento") String documento) {
        return ResponseEntity.ok(clienteService.findClienteByDocumento(documento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable("id") String id) {
        clienteService.deleteById(Integer.parseInt(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }
}
