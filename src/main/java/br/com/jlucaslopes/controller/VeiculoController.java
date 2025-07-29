package br.com.jlucaslopes.controller;


import br.com.jlucaslopes.model.Veiculo;
import br.com.jlucaslopes.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {


    private final VeiculoService veiculoService;

    @Autowired
    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public Veiculo salvar(@RequestBody Veiculo veiculo) {
        return veiculoService.salvar(veiculo);
    }

    @GetMapping("/{id}")
    public Veiculo buscarPorId(@PathVariable Long id) {
        return veiculoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @RequestBody Veiculo veiculoAtualizado) {
        return veiculoService.atualizar(id, veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
    }

    @GetMapping("/cliente/{documento}")
    public List<Veiculo> findVeiculosByClienteDocumento(@PathVariable String documento) {
        return veiculoService.findVeiculosByClienteDocumento(documento);
    }

}
