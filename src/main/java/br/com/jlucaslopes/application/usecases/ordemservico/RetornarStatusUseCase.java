package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;

public class RetornarStatusUseCase {
    private final OrdemServicoGateway gateway;

    public RetornarStatusUseCase(OrdemServicoGateway gateway) {
        this.gateway = gateway;
    }

    public OrdemServico retornarStatus(Long ordemServicoId) {
        return gateway.retornarStatus(ordemServicoId);
    }
}
