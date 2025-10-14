package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.domain.entities.Peca;

public class AtualizarPecaUseCase {

    private PecaGateway pecaGateway;

    public AtualizarPecaUseCase(PecaGateway pecaGateway) {
        this.pecaGateway = pecaGateway;
    }

    public Peca atualizarPeca(Long id, Peca peca){
        return pecaGateway.atualizar(id, peca);
    }
}
