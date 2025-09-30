package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;

public class DeleteVeiculoByIdUseCase {
    private final VeiculoGateway veiculoGateway;

    public DeleteVeiculoByIdUseCase(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    public void deleteById(Long id) {
        this.veiculoGateway.deletar(id);
    }
}
