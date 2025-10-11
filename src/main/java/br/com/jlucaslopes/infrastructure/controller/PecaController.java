package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.application.usecases.peca.*;
import br.com.jlucaslopes.domain.entities.Peca;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/peca")
@Tag(name = "Peca", description = "Operações relacionadas a gestao de Peças e estoque")
public class PecaController {

    private final AtualizarEstoquePecaUseCase atualizarEstoquePecaUseCase;
    private final AtualizarPecaUseCase atualizarPecaUseCase;
    private final BuscarPecaPorIdUseCase buscarPecaPorIdUseCase;
    private final DeletarPecaUseCase deletarPecaUseCase;
    private final SalvarPecaUseCase salvarPecaUseCase;

    public PecaController(AtualizarEstoquePecaUseCase atualizarEstoquePecaUseCase,
                          AtualizarPecaUseCase atualizarPecaUseCase,
                          BuscarPecaPorIdUseCase buscarPecaPorIdUseCase,
                          DeletarPecaUseCase deletarPecaUseCase,
                          SalvarPecaUseCase salvarPecaUseCase) {
        this.atualizarEstoquePecaUseCase = atualizarEstoquePecaUseCase;
        this.atualizarPecaUseCase = atualizarPecaUseCase;
        this.buscarPecaPorIdUseCase = buscarPecaPorIdUseCase;
        this.deletarPecaUseCase = deletarPecaUseCase;
        this.salvarPecaUseCase = salvarPecaUseCase;
    }


    @PostMapping
    public ResponseEntity<Peca> salvar(@RequestBody Peca peca) {
        return ResponseEntity.ok(salvarPecaUseCase.salvarPeca(peca));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar peça por ID", description = "Retorna os dados da peça correspondente ao ID informado.")
    public ResponseEntity<Peca> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(buscarPecaPorIdUseCase.buscarPecaPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar peça por ID", description = "Atualiza os dados da peça correspondente ao ID informado.")
    public ResponseEntity<Peca> atualizar(@PathVariable("id") Long id, @RequestBody Peca pecaAtualizada) {
        return ResponseEntity.ok(atualizarPecaUseCase.atualizarPeca(id, pecaAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar peça por ID", description = "Remove a peça correspondente ao ID informado.")
    public ResponseEntity<Object> deletar(@PathVariable("id") Long id) {
        deletarPecaUseCase.deletarPeca(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estoque")
    @Operation(summary = "Atualizar estoque da peça", description = "Atualiza a quantidade em estoque da peça especificada pelo ID.")
    public ResponseEntity<Peca> atualizaEstoque(@PathVariable("id") Long id, @RequestParam("quantidadeAdicionada") int quantidadeAdicionada) {
        return ResponseEntity.ok(atualizarEstoquePecaUseCase.atualizarEstoquePeca(id, quantidadeAdicionada));
    }
}