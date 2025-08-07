package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.OrdemServico;
import br.com.jlucaslopes.model.Servico;
import br.com.jlucaslopes.model.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.service.OrdemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens")
public class OrdemController {

    private final OrdemService ordemService;

    @Autowired
    public OrdemController(OrdemService ordemService) {
        this.ordemService = ordemService;
    }

    @PostMapping
    public OrdemServico criarOrdem(@RequestBody OrdemServicoCreateRequest request) {
        return ordemService.criarOrdemServico(request);
    }

    @GetMapping("/{id}")
    public OrdemServico buscarPorId(@PathVariable("id") Long id) {
        return ordemService.buscarOrdemPorId(id);
    }

    @PostMapping("/{id}/servicos")
    public OrdemServico adicionarServico(@PathVariable("id") Long id, @RequestBody Servico servico) {
        return ordemService.adicionarServico(id, servico);
    }

    @PostMapping("/{id}/avancar")
    public OrdemServico avancarStatus(@PathVariable("id") Long id) {
        return ordemService.avancarStatus(id);
    }

    @PostMapping("/{id}/retornar")
    public OrdemServico retornarStatus(@PathVariable("id") Long id) {
        return ordemService.retornarStatus(id);
    }

    @GetMapping("/tempo-medio")
    public String tempoMedio() {
        return ordemService.retornaTempoMedioDeOrdemServico();
    }
}