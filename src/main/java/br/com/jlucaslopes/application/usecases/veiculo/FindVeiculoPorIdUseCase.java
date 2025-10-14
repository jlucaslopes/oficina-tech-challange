package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Veiculo;

public class FindVeiculoPorIdUseCase {
    private final VeiculoGateway veiculoGateway;

    public FindVeiculoPorIdUseCase(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    public Veiculo findVeiculoPorId(Long id) {
        return this.veiculoGateway.buscarPorId(id);
    }
}
