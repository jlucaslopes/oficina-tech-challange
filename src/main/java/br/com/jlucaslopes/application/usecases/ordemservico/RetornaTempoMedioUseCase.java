package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;

public class RetornaTempoMedioUseCase {

    private final OrdemServicoGateway ordemServicoGateway;

    public RetornaTempoMedioUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public String retornaTempoMedioDeOrdemServico() {
        return ordemServicoGateway.retornaTempoMedioDeOrdemServico();
    }
}
