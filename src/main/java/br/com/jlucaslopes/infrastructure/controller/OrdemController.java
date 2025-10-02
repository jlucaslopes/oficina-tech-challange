package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.usecases.ordemservico.*;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens")
@Tag(name = "Ordem", description = "Operações relacionadas a gestao de Ordens de Serviço")
public class OrdemController {

    private final AdicionarServicoUseCase adicionarServicoUseCase;
    private final AvancarStatusUseCase avancarStatusUseCase;
    private final BuscaOrdemPorIdUseCase buscarOrdemPorIdUseCase;
    private final CancelarOrdemUseCase cancelarOrdemUseCase;
    private final CriaOrdemServicoUseCase criaOrdemServicoUseCase;
    private final RetornarStatusUseCase retornarStatusUseCase;
    private final RetornaTempoMedioUseCase retornaTempoMedioUseCase;
    private final ConsultarStatusUseCase consultarStatusUseCase;

    public OrdemController(AdicionarServicoUseCase adicionarServicoUseCase,
                           AvancarStatusUseCase avancarStatusUseCase,
                           BuscaOrdemPorIdUseCase buscarOrdemPorIdUseCase,
                           CancelarOrdemUseCase cancelarOrdemUseCase,
                           CriaOrdemServicoUseCase criaOrdemServicoUseCase,
                           RetornarStatusUseCase retornarStatusUseCase,
                           RetornaTempoMedioUseCase retornaTempoMedioUseCase,
                           ConsultarStatusUseCase consultarStatusUseCase) {
        this.adicionarServicoUseCase = adicionarServicoUseCase;
        this.avancarStatusUseCase = avancarStatusUseCase;
        this.buscarOrdemPorIdUseCase = buscarOrdemPorIdUseCase;
        this.cancelarOrdemUseCase = cancelarOrdemUseCase;
        this.criaOrdemServicoUseCase = criaOrdemServicoUseCase;
        this.retornarStatusUseCase = retornarStatusUseCase;
        this.retornaTempoMedioUseCase = retornaTempoMedioUseCase;
        this.consultarStatusUseCase = consultarStatusUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar nova ordem", description = "Cadastra uma nova ordem no sistema.")
    public OrdemServico criarOrdem(@RequestBody OrdemServicoCreateRequest request) {
        return criaOrdemServicoUseCase.criarOrdemServico(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca ordem por id", description = "Retornar os dados da ordem de serviço correspondente ao ID informado.")
    public OrdemServico buscarPorId(@PathVariable("id") Long id) {
        return buscarOrdemPorIdUseCase.buscarOrdemPorId(id);
    }

    @PostMapping("/{id}/servicos")
    @Operation(summary = "Adicionar serviço à ordem", description = "Adiciona um serviço à ordem de serviço especificada pelo ID.")
    public OrdemServico adicionarServico(@PathVariable("id") Long id, @RequestBody ServicoCreateRequest servico) {
        return adicionarServicoUseCase.adicionarServico(id, servico);
    }

    @PostMapping("/{id}/avancar")
    @Operation(summary = "Avançar status da ordem", description = "Avança o status da ordem de serviço para o próximo estágio.")
    public OrdemServico avancarStatus(@PathVariable("id") Long id) {
        return avancarStatusUseCase.avancarStatus(id);
    }

    @PostMapping("/{id}/retornar")
    @Operation(summary = "Retornar status da ordem", description = "Retorna o status da ordem de serviço para o estágio anterior.")
    public OrdemServico retornarStatus(@PathVariable("id") Long id) {
        return retornarStatusUseCase.retornarStatus(id);
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Consultar status da ordem", description = "Retorna o status atual da ordem de serviço especificada pelo ID.")
    public String consultarStatus(@PathVariable("id") Long id) {
        return consultarStatusUseCase.consultarStatus(id);
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar ordem", description = "Cancela a ordem de serviço especificada pelo ID.")
    public OrdemServico cancelarOrdem(@PathVariable("id") Long id) {
        return cancelarOrdemUseCase.cancelarOrdem(id);
    }

    @GetMapping("/tempo-medio")
    @Operation(summary = "Tempo médio de conclusão", description = "Retorna o tempo médio de conclusão das ordens de serviço.")
    public String tempoMedio() {
        return retornaTempoMedioUseCase.retornaTempoMedioDeOrdemServico();
    }
}