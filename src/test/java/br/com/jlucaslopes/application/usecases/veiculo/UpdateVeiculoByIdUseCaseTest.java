package br.com.jlucaslopes.application.usecases.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import br.com.jlucaslopes.domain.entities.Veiculo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateVeiculoByIdUseCaseTest {

    @Mock
    private VeiculoGateway veiculoGateway;

    @Test
    void shouldUpdateAndReturnVeiculo() {
        UpdateVeiculoByIdUseCase useCase = new UpdateVeiculoByIdUseCase(veiculoGateway);

        Veiculo updated = new Veiculo(5L, "CCC3333", "Toyota", "Corolla", "2020", new Cliente(3L, "X", "22233344455"));

        when(veiculoGateway.atualizar(5L, updated)).thenReturn(updated);

        Veiculo result = useCase.updateById(5L, updated);

        assertNotNull(result);
        assertEquals(updated.getId(), result.getId());
        verify(veiculoGateway, times(1)).atualizar(5L, updated);
    }
}
