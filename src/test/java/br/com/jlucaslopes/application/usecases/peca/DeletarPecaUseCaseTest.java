package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarPecaUseCaseTest {

    @Mock
    private PecaGateway pecaGateway;

    @Test
    void shouldCallDeleteOnGateway() {
        DeletarPecaUseCase useCase = new DeletarPecaUseCase(pecaGateway);

        useCase.deletarPeca(11L);

        verify(pecaGateway, times(1)).deletar(11L);
    }
}
