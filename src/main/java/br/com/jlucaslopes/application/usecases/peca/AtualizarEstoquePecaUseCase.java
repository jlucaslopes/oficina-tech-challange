package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.domain.entities.Peca;

public class AtualizarEstoquePecaUseCase {
    private final PecaGateway pecaGateway;

    public AtualizarEstoquePecaUseCase(PecaGateway pecaGateway) {
        this.pecaGateway = pecaGateway;
    }

    public Peca atualizarEstoquePeca(Long id, int quantidade) {
        return pecaGateway.atualizaEstoque(id, quantidade);
    }
}
