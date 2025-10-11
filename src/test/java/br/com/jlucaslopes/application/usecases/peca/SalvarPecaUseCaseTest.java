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
class SalvarPecaUseCaseTest {

    @Mock
    private PecaGateway pecaGateway;

    @Test
    void shouldSavePeca() {
        SalvarPecaUseCase useCase = new SalvarPecaUseCase(pecaGateway);

        Peca peca = new Peca(null, "Filtro", 50.0, 10);
        Peca saved = new Peca(1L, "Filtro", 50.0, 10);

        when(pecaGateway.salvar(peca)).thenReturn(saved);

        Peca result = useCase.salvarPeca(peca);

        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
        verify(pecaGateway, times(1)).salvar(peca);
    }
}
