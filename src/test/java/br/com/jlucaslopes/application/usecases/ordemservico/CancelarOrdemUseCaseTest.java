package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarOrdemUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldCancelOrder() {
        CancelarOrdemUseCase useCase = new CancelarOrdemUseCase(gateway);

        OrdemServico o = new OrdemServico();
        o.setId(8L);

        when(gateway.cancelarOrdem(8L)).thenReturn(o);

        OrdemServico result = useCase.cancelarOrdem(8L);

        assertNotNull(result);
        assertEquals(8L, result.getId());
        verify(gateway, times(1)).cancelarOrdem(8L);
    }
}
