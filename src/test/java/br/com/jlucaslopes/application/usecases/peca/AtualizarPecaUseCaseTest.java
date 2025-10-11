package br.com.jlucaslopes.application.usecases.peca;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.domain.entities.Peca;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarPecaUseCaseTest {

    @Mock
    private PecaGateway pecaGateway;

    @Test
    void shouldUpdatePeca() {
        AtualizarPecaUseCase useCase = new AtualizarPecaUseCase(pecaGateway);

        Peca atualizada = new Peca(20L, "Bateria", 300.0, 7);

        when(pecaGateway.atualizar(20L, atualizada)).thenReturn(atualizada);

        Peca result = useCase.atualizarPeca(20L, atualizada);

        assertNotNull(result);
        assertEquals(atualizada.getId(), result.getId());
        verify(pecaGateway, times(1)).atualizar(20L, atualizada);
    }
}
