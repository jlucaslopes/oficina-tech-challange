package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.Peca;
import br.com.jlucaslopes.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/peca")
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
    public ResponseEntity<Peca> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pecaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizar(@PathVariable("id") Long id, @RequestBody Peca pecaAtualizada) {
        return ResponseEntity.ok(pecaService.atualizar(id, pecaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        pecaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estoque")
    public ResponseEntity<Peca> atualizaEstoque(@PathVariable("id") Long id, @RequestParam("quantidadeAdicionada") int quantidadeAdicionada) {
        return ResponseEntity.ok(pecaService.atualizaEstoque(id, quantidadeAdicionada));
    }
}