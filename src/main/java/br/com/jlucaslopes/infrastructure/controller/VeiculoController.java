package br.com.jlucaslopes.infrastructure.controller;


import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.entities.request.VeiculoCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
@Tag(name = "Veiculos", description = "Operações relacionadas a gestao de veiculos de clientes")
public class VeiculoController {


    private final VeiculoGateway veiculoGateway;

    public VeiculoController(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    @PostMapping
    @Operation(summary = "Criar novo veiculo", description = "Cadastra um novo veiculo no sistema.")
    public Veiculo salvar(@RequestBody VeiculoCreateRequest veiculo) {
        return veiculoGateway.salvar(veiculo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veiculo por ID", description = "Retorna os dados do veiculo correspondente ao ID informado.")
    public Veiculo buscarPorId(@PathVariable("id") Long id) {
        return veiculoGateway.buscarPorId(id);
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar veiculo por placa", description = "Retorna os dados do veiculo correspondente a placa informada.")
    public ResponseEntity<Veiculo> buscarPorPlaca(@PathVariable("placa") String placa) {
        return ResponseEntity.ok(veiculoGateway.findVeiculoByPlaca(placa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veiculo por ID", description = "Atualiza os dados do veiculo correspondente ao ID informado.")
    public Veiculo atualizar(@PathVariable("id") Long id, @RequestBody Veiculo veiculoAtualizado) {
        return veiculoGateway.atualizar(id, veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veiculo por ID", description = "Remove o veiculo correspondente ao ID informado.")
    public void deletar(@PathVariable("id") Long id) {
        veiculoGateway.deletar(id);
    }

    @GetMapping("/cliente/{documento}")
    @Operation(summary = "Buscar veiculos por documento do cliente", description = "Retorna a lista de veiculos associados ao cliente com o documento informado.")
    public List<Veiculo> findVeiculosByClienteDocumento(@PathVariable("documento") String documento) {
        return veiculoGateway.findVeiculosByClienteDocumento(documento);
    }

}
