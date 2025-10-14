package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;

public class BuscaOrdemPorIdUseCase {

    private final OrdemServicoGateway ordemServicoGateway;

    public BuscaOrdemPorIdUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public OrdemServico buscarOrdemPorId(Long id) {
        return ordemServicoGateway.buscarOrdemPorId(id);
    }
}
