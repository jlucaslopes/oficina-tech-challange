package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;

import java.util.List;

public class ListarOrdensUseCase {
    private final OrdemServicoGateway ordemServicoGateway;

    public ListarOrdensUseCase(OrdemServicoGateway ordemServicoGateway) {
        this.ordemServicoGateway = ordemServicoGateway;
    }

    public List<OrdemServico> listarOrdensDeServico() {
        return this.ordemServicoGateway.listarOrdemServicos();
    }
}
