package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import br.com.jlucaslopes.domain.entities.Veiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindVeiculoPorIdUseCaseTest {

    @Mock
    private VeiculoGateway veiculoGateway;

    @Test
    void shouldReturnVeiculoWhenFound() {
        FindVeiculoPorIdUseCase useCase = new FindVeiculoPorIdUseCase(veiculoGateway);

        Veiculo v = new Veiculo(2L, "DEF4321", "Volks", "Gol", "2015", new Cliente(2L, "Nome", "98765432100"));

        when(veiculoGateway.buscarPorId(2L)).thenReturn(v);

        Veiculo result = useCase.findVeiculoPorId(2L);

        assertNotNull(result);
        assertEquals(v.getId(), result.getId());
        verify(veiculoGateway, times(1)).buscarPorId(2L);
    }

    @Test
    void shouldReturnNullWhenNotFound() {
        FindVeiculoPorIdUseCase useCase = new FindVeiculoPorIdUseCase(veiculoGateway);

        when(veiculoGateway.buscarPorId(99L)).thenReturn(null);

        Veiculo result = useCase.findVeiculoPorId(99L);

        assertNull(result);
        verify(veiculoGateway, times(1)).buscarPorId(99L);
    }
}
