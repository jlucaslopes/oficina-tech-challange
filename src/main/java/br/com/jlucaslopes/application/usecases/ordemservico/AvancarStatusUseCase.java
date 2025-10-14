package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;

public class AvancarStatusUseCase {
    private final OrdemServicoGateway gateway;

    public AvancarStatusUseCase(OrdemServicoGateway gateway) {
        this.gateway = gateway;
    }

    public OrdemServico avancarStatus(Long ordemServicoId) {
        return gateway.avancarStatus(ordemServicoId);
    }
}
