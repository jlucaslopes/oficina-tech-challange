package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Veiculo;

public class FindVeiculoByPlacaUseCase {
    private final VeiculoGateway veiculoGateway;

    public FindVeiculoByPlacaUseCase(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    public Veiculo findVeiculoByPlaca(String placa) {
        return this.veiculoGateway.findVeiculoByPlaca(placa);
    }
}
