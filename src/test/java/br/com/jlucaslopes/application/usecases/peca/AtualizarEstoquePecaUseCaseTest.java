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
class AtualizarEstoquePecaUseCaseTest {

    @Mock
    private PecaGateway pecaGateway;

    @Test
    void shouldUpdateEstoqueAndReturnPeca() {
        AtualizarEstoquePecaUseCase useCase = new AtualizarEstoquePecaUseCase(pecaGateway);

        Peca updated = new Peca(10L, "Parafuso", 1.5, 50);

        when(pecaGateway.atualizaEstoque(10L, 5)).thenReturn(updated);

        Peca result = useCase.atualizarEstoquePeca(10L, 5);

        assertNotNull(result);
        assertEquals(updated.getQuantidadeEstoque(), result.getQuantidadeEstoque());
        verify(pecaGateway, times(1)).atualizaEstoque(10L, 5);
    }
}
