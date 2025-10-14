package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;

public class AdicionarServicoUseCase {
    private final OrdemServicoGateway ordemServicoGateway;

    public AdicionarServicoUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public OrdemServico adicionarServico(Long ordemId, ServicoCreateRequest servicoRequest) {
        return ordemServicoGateway.adicionarServico(ordemId, servicoRequest);
    }

}
