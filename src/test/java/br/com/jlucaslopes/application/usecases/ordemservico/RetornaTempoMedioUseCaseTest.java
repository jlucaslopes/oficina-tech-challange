package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetornaTempoMedioUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldReturnTempoMedioString() {
        RetornaTempoMedioUseCase useCase = new RetornaTempoMedioUseCase(gateway);

    when(gateway.retornaTempoMedioDeOrdemServico()).thenReturn("2 dias");

    String result = useCase.retornaTempoMedioDeOrdemServico();

    assertEquals("2 dias", result);
    verify(gateway, times(1)).retornaTempoMedioDeOrdemServico();
    }
}
