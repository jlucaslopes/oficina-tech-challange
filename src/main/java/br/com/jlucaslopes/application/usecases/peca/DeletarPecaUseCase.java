package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;

public class DeletarPecaUseCase {

    private final PecaGateway pecaGateway;

    public DeletarPecaUseCase(PecaGateway pecaGateway) {
        this.pecaGateway = pecaGateway;
    }

    public void deletarPeca(Long id) {
        pecaGateway.deletar(id);
    }
}
