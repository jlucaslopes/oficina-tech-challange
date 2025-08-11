package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.OrdemServico;
import br.com.jlucaslopes.model.Servico;
import br.com.jlucaslopes.model.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.service.OrdemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens")
@Tag(name = "Ordem", description = "Operações relacionadas a gestao de Ordens de Serviço")
public class OrdemController {

    private final OrdemService ordemService;

    @Autowired
    public OrdemController(OrdemService ordemService) {
        this.ordemService = ordemService;
    }

    @PostMapping
    @Operation(summary = "Criar nova ordem", description = "Cadastra uma nova ordem no sistema.")
    public OrdemServico criarOrdem(@RequestBody OrdemServicoCreateRequest request) {
        return ordemService.criarOrdemServico(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca ordem por id", description = "Retornar os dados da ordem de serviço correspondente ao ID informado.")
    public OrdemServico buscarPorId(@PathVariable("id") Long id) {
        return ordemService.buscarOrdemPorId(id);
    }

    @PostMapping("/{id}/servicos")
    @Operation(summary = "Adicionar serviço à ordem", description = "Adiciona um serviço à ordem de serviço especificada pelo ID.")
    public OrdemServico adicionarServico(@PathVariable("id") Long id, @RequestBody Servico servico) {
        return ordemService.adicionarServico(id, servico);
    }

    @PostMapping("/{id}/avancar")
    @Operation(summary = "Avançar status da ordem", description = "Avança o status da ordem de serviço para o próximo estágio.")
    public OrdemServico avancarStatus(@PathVariable("id") Long id) {
        return ordemService.avancarStatus(id);
    }

    @PostMapping("/{id}/retornar")
    @Operation(summary = "Retornar status da ordem", description = "Retorna o status da ordem de serviço para o estágio anterior.")
    public OrdemServico retornarStatus(@PathVariable("id") Long id) {
        return ordemService.retornarStatus(id);
    }


    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar ordem", description = "Cancela a ordem de serviço especificada pelo ID.")
    public OrdemServico cancelarOrdem(@PathVariable("id") Long id) {
        return ordemService.cancelarOrdem(id);
    }

    @GetMapping("/tempo-medio")
    @Operation(summary = "Tempo médio de conclusão", description = "Retorna o tempo médio de conclusão das ordens de serviço.")
    public String tempoMedio() {
        return ordemService.retornaTempoMedioDeOrdemServico();
    }
}