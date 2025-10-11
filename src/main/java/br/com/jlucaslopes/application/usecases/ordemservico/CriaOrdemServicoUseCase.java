package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;

public class CriaOrdemServicoUseCase {

    private final OrdemServicoGateway ordemServicoGateway;

    public CriaOrdemServicoUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public OrdemServico criarOrdemServico(OrdemServicoCreateRequest request) {
        return ordemServicoGateway.criarOrdemServico(request);
    }
}
