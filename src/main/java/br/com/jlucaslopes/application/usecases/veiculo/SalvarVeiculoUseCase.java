package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.request.VeiculoCreateRequest;

public class SalvarVeiculoUseCase {
    private final VeiculoGateway veiculoGateway;

    public SalvarVeiculoUseCase(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    public Veiculo salvar(VeiculoCreateRequest request) {
        return this.veiculoGateway.salvar(request);
    }
}
