package br.com.jlucaslopes.naousar.controller;

import br.com.jlucaslopes.naousar.model.Peca;
import br.com.jlucaslopes.naousar.service.PecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/peca")
@Tag(name = "Peca", description = "Operações relacionadas a gestao de Peças e estoque")
public class PecaController {

    private final PecaService pecaService;

    @Autowired
    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @PostMapping
    public ResponseEntity<Peca> salvar(@RequestBody Peca peca) {
        return ResponseEntity.ok(pecaService.salvar(peca));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar peça por ID", description = "Retorna os dados da peça correspondente ao ID informado.")
    public ResponseEntity<Peca> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pecaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar peça por ID", description = "Atualiza os dados da peça correspondente ao ID informado.")
    public ResponseEntity<Peca> atualizar(@PathVariable("id") Long id, @RequestBody Peca pecaAtualizada) {
        return ResponseEntity.ok(pecaService.atualizar(id, pecaAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar peça por ID", description = "Remove a peça correspondente ao ID informado.")
    public ResponseEntity<Object> deletar(@PathVariable("id") Long id) {
        pecaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estoque")
    @Operation(summary = "Atualizar estoque da peça", description = "Atualiza a quantidade em estoque da peça especificada pelo ID.")
    public ResponseEntity<Peca> atualizaEstoque(@PathVariable("id") Long id, @RequestParam("quantidadeAdicionada") int quantidadeAdicionada) {
        return ResponseEntity.ok(pecaService.atualizaEstoque(id, quantidadeAdicionada));
    }
}