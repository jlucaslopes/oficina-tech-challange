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
class BuscarPecaPorIdUseCaseTest {

    @Mock
    private PecaGateway gateway;

    @Test
    void shouldReturnPecaWhenFound() {
        BuscarPecaPorIdUseCase useCase = new BuscarPecaPorIdUseCase(gateway);

        Peca p = new Peca(7L, "Correia", 120.0, 5);

        when(gateway.buscarPorId(7L)).thenReturn(p);

        Peca result = useCase.buscarPecaPorId(7L);

        assertNotNull(result);
        assertEquals(p.getId(), result.getId());
        verify(gateway, times(1)).buscarPorId(7L);
    }

    @Test
    void shouldReturnNullWhenNotFound() {
        BuscarPecaPorIdUseCase useCase = new BuscarPecaPorIdUseCase(gateway);

        when(gateway.buscarPorId(999L)).thenReturn(null);

        Peca result = useCase.buscarPecaPorId(999L);

        assertNull(result);
        verify(gateway, times(1)).buscarPorId(999L);
    }
}
