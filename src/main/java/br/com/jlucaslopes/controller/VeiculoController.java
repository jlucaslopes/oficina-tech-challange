package br.com.jlucaslopes.controller;


import br.com.jlucaslopes.model.Veiculo;
import br.com.jlucaslopes.model.request.VeiculoCreateRequest;
import br.com.jlucaslopes.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Veiculo salvar(@RequestBody VeiculoCreateRequest veiculo) {
        return veiculoService.salvar(veiculo);
    }

    @GetMapping("/{id}")
    public Veiculo buscarPorId(@PathVariable("id") Long id) {
        return veiculoService.buscarPorId(id);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Veiculo> buscarPorPlaca(@PathVariable("placa") String placa) {
        return ResponseEntity.ok(veiculoService.findVeiculoByPlaca(placa));
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable("id") Long id, @RequestBody Veiculo veiculoAtualizado) {
        return veiculoService.atualizar(id, veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable("id") Long id) {
        veiculoService.deletar(id);
    }

    @GetMapping("/cliente/{documento}")
    public List<Veiculo> findVeiculosByClienteDocumento(@PathVariable("documento") String documento) {
        return veiculoService.findVeiculosByClienteDocumento(documento);
    }

}
