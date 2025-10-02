package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.domain.entities.Peca;

public class SalvarPecaUseCase {

    private final PecaGateway pecaGateway;

    public SalvarPecaUseCase(PecaGateway pecaGateway) {
        this.pecaGateway = pecaGateway;
    }

    public Peca salvarPeca(Peca peca) {
        return pecaGateway.salvar(peca);
    }
}
