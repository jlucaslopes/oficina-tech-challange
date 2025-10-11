package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarOrdensUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldListOrdens() {
        ListarOrdensUseCase useCase = new ListarOrdensUseCase(gateway);

        OrdemServico o1 = new OrdemServico();
        o1.setId(1L);
        OrdemServico o2 = new OrdemServico();
        o2.setId(2L);

        when(gateway.listarOrdemServicos()).thenReturn(List.of(o1, o2));

        var result = useCase.listarOrdensDeServico();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gateway, times(1)).listarOrdemServicos();
    }
}
