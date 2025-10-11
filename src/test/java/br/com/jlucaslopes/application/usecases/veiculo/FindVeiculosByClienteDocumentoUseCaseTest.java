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
class FindVeiculosByClienteDocumentoUseCaseTest {

    @Mock
    private VeiculoGateway veiculoGateway;

    @Test
    void shouldReturnListOfVeiculos() {
        FindVeiculosByClienteDocumentoUseCase useCase = new FindVeiculosByClienteDocumentoUseCase(veiculoGateway);

        Veiculo v1 = new Veiculo(1L, "AAA1111", "Ford", "Ka", "2012", new Cliente(1L, "A", "11122233344"));
        Veiculo v2 = new Veiculo(2L, "BBB2222", "Chev", "Celta", "2013", new Cliente(1L, "A", "11122233344"));

        when(veiculoGateway.findVeiculosByClienteDocumento("11122233344")).thenReturn(List.of(v1, v2));

        List<Veiculo> result = useCase.findVeiculosByClienteDocumento("11122233344");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(veiculoGateway, times(1)).findVeiculosByClienteDocumento("11122233344");
    }
}
