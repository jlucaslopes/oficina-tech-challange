package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdicionarServicoUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldAddServicoToOrdem() {
        AdicionarServicoUseCase useCase = new AdicionarServicoUseCase(gateway);

        ServicoCreateRequest req = new ServicoCreateRequest();
        req.setNome("Troca óleo");
        req.setQuantidade(1L);
        req.setPreco(100.0);

        OrdemServico updated = new OrdemServico();
        updated.setId(5L);

        when(gateway.adicionarServico(5L, req)).thenReturn(updated);

        OrdemServico result = useCase.adicionarServico(5L, req);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        verify(gateway, times(1)).adicionarServico(5L, req);
    }
}
