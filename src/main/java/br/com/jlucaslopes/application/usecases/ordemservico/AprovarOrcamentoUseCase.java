package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;

public class AprovarOrcamentoUseCase {
    private final OrdemServicoGateway ordemServicoGateway;

    public AprovarOrcamentoUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public void aprovarOrcamento(Long id, boolean aprovado) {
        this.ordemServicoGateway.aprovaOrcamento(id,aprovado );
    }
}
