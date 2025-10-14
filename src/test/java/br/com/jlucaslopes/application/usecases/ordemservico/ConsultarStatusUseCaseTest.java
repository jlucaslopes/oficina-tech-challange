package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarStatusUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldReturnStatusString() {
        ConsultarStatusUseCase useCase = new ConsultarStatusUseCase(gateway);

        when(gateway.consultarStatus(4L)).thenReturn("EM_ANDAMENTO");

        String result = useCase.consultarStatus(4L);

        assertNotNull(result);
        assertEquals("EM_ANDAMENTO", result);
        verify(gateway, times(1)).consultarStatus(4L);
    }
}
