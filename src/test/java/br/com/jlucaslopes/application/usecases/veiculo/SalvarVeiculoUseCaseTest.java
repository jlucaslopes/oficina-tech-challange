package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.request.VeiculoCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalvarVeiculoUseCaseTest {

    @Mock
    private VeiculoGateway veiculoGateway;

    @Test
    void shouldSaveVeiculo() {
        SalvarVeiculoUseCase useCase = new SalvarVeiculoUseCase(veiculoGateway);

        VeiculoCreateRequest request = new VeiculoCreateRequest("ABC1234", "Fiat", "Uno", "2010", "12345678901");
        Veiculo saved = new Veiculo(1L, "ABC1234", "Fiat", "Uno", "2010", new Cliente(1L, "Cliente", "12345678901"));

        when(veiculoGateway.salvar(request)).thenReturn(saved);

        Veiculo result = useCase.salvar(request);

        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getPlaca(), result.getPlaca());

        verify(veiculoGateway, times(1)).salvar(request);
    }
}
