package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;

public class CancelarOrdemUseCase {
    private final OrdemServicoGateway gateway;

    public CancelarOrdemUseCase(OrdemServicoGateway gateway) {
        this.gateway = gateway;
    }

    public OrdemServico cancelarOrdem(Long ordemServicoId) {
        return gateway.cancelarOrdem(ordemServicoId);
    }
}
