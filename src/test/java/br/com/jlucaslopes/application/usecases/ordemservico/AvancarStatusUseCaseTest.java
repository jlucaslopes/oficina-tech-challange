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
class AvancarStatusUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldAdvanceStatus() {
        AvancarStatusUseCase useCase = new AvancarStatusUseCase(gateway);

        OrdemServico o = new OrdemServico();
        o.setId(3L);

        when(gateway.avancarStatus(3L)).thenReturn(o);

        OrdemServico result = useCase.avancarStatus(3L);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        verify(gateway, times(1)).avancarStatus(3L);
    }
}
