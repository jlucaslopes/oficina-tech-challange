package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.domain.entities.Peca;

public class BuscarPecaPorIdUseCase {
    private final PecaGateway gateway;

    public BuscarPecaPorIdUseCase(PecaGateway gateway) {
        this.gateway = gateway;
    }

    public Peca buscarPecaPorId(Long id) {
        return gateway.buscarPorId(id);
    }
}
