package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.Status;

public class ConsultarStatusUseCase {

    private final OrdemServicoGateway ordemServicoGateway;

    public ConsultarStatusUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public Status consultarStatus(Long id) {
        return this.ordemServicoGateway.consultarStatus(id);
    }
}
