package br.com.jlucaslopes.infrastructure.controller;


import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.application.usecases.veiculo.*;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.request.VeiculoCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
@Tag(name = "Veiculos", description = "Operações relacionadas a gestao de veiculos de clientes")
public class VeiculoController {


    private final DeleteVeiculoByIdUseCase deleteVeiculoByIdUseCase;
    private final FindVeiculoByPlacaUseCase findVeiculoByPlacaUseCase;
    private final FindVeiculoPorIdUseCase findVeiculoPorIdUseCase;
    private final FindVeiculosByClienteDocumentoUseCase findVeiculosByClienteDocumentoUseCase;
    private final SalvarVeiculoUseCase salvarVeiculoUseCase;
    private final UpdateVeiculoByIdUseCase updateVeiculoByIdUseCase;

    public VeiculoController(DeleteVeiculoByIdUseCase deleteVeiculoByIdUseCase,
                             FindVeiculoByPlacaUseCase findVeiculoByPlacaUseCase,
                             FindVeiculoPorIdUseCase findVeiculoPorIdUseCase,
                             FindVeiculosByClienteDocumentoUseCase findVeiculosByClienteDocumentoUseCase,
                             SalvarVeiculoUseCase salvarVeiculoUseCase,
                             UpdateVeiculoByIdUseCase updateVeiculoByIdUseCase) {
        this.deleteVeiculoByIdUseCase = deleteVeiculoByIdUseCase;
        this.findVeiculoByPlacaUseCase = findVeiculoByPlacaUseCase;
        this.findVeiculoPorIdUseCase = findVeiculoPorIdUseCase;
        this.findVeiculosByClienteDocumentoUseCase = findVeiculosByClienteDocumentoUseCase;
        this.salvarVeiculoUseCase = salvarVeiculoUseCase;
        this.updateVeiculoByIdUseCase = updateVeiculoByIdUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar novo veiculo", description = "Cadastra um novo veiculo no sistema.")
    public Veiculo salvar(@RequestBody VeiculoCreateRequest veiculo) {
        return salvarVeiculoUseCase.salvar(veiculo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veiculo por ID", description = "Retorna os dados do veiculo correspondente ao ID informado.")
    public Veiculo buscarPorId(@PathVariable("id") Long id) {
        return findVeiculoPorIdUseCase.findVeiculoPorId(id);
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar veiculo por placa", description = "Retorna os dados do veiculo correspondente a placa informada.")
    public ResponseEntity<Veiculo> buscarPorPlaca(@PathVariable("placa") String placa) {
        return ResponseEntity.ok(findVeiculoByPlacaUseCase.findVeiculoByPlaca(placa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar veiculo por ID", description = "Atualiza os dados do veiculo correspondente ao ID informado.")
    public Veiculo atualizar(@PathVariable("id") Long id, @RequestBody Veiculo veiculoAtualizado) {
        return updateVeiculoByIdUseCase.updateById(id,veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veiculo por ID", description = "Remove o veiculo correspondente ao ID informado.")
    public void deletar(@PathVariable("id") Long id) {
        deleteVeiculoByIdUseCase.deleteById(id);
    }

    @GetMapping("/cliente/{documento}")
    @Operation(summary = "Buscar veiculos por documento do cliente", description = "Retorna a lista de veiculos associados ao cliente com o documento informado.")
    public List<Veiculo> findVeiculosByClienteDocumento(@PathVariable("documento") String documento) {
        return findVeiculosByClienteDocumentoUseCase.findVeiculosByClienteDocumento(documento);
    }

}
