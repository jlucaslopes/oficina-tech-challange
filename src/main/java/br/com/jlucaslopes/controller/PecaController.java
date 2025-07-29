package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.Peca;
import br.com.jlucaslopes.service.PecaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    private final PecaService pecaService;

    @Autowired
    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @PostMapping
    public Peca salvar(@RequestBody Peca peca) {
        return pecaService.salvar(peca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pecaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizar(@PathVariable Long id, @RequestBody Peca pecaAtualizada) {
        return pecaService.atualizar(id, pecaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        pecaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Peca> atualizaEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        return pecaService.atualizaEstoque(id, quantidade);
    }
}