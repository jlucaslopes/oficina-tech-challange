package br.com.jlucaslopes.application.usecases.ordemservico;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriaOrdemServicoUseCaseTest {

    @Mock
    private OrdemServicoGateway gateway;

    @Test
    void shouldCreateOrdemServico() {
        CriaOrdemServicoUseCase useCase = new CriaOrdemServicoUseCase(gateway);

        OrdemServicoCreateRequest request = new OrdemServicoCreateRequest();
        request.setPlacaVeiculo("AAA1111");
        request.setDescricao("Troca de óleo");

        OrdemServico created = new OrdemServico();
        created.setId(1L);

        when(gateway.criarOrdemServico(request)).thenReturn(created);

        OrdemServico result = useCase.criarOrdemServico(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(gateway, times(1)).criarOrdemServico(request);
    }
}
