package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Veiculo;

import java.util.List;

public class FindVeiculosByClienteDocumentoUseCase {
    private final VeiculoGateway veiculoGateway;

    public FindVeiculosByClienteDocumentoUseCase(VeiculoGateway veiculoGateway) {
        this.veiculoGateway = veiculoGateway;
    }

    public List<Veiculo> findVeiculosByClienteDocumento(String documento) {
       return this.veiculoGateway.findVeiculosByClienteDocumento(documento);
    }
}
