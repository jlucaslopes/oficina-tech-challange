package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Veiculo;

public class UpdateVeiculoByIdUseCase {

    private final VeiculoGateway veiculoGateway;

    public UpdateVeiculoByIdUseCase(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    public Veiculo updateById(Long id, Veiculo veiculoAtualizado) {
        return this.veiculoGateway.atualizar(id, veiculoAtualizado);
    }

}
