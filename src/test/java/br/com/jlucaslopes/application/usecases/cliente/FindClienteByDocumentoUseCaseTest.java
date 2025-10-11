package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindClienteByDocumentoUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    void shouldReturnClienteWhenDocumentoExists() {
        FindClienteByDocumentoUseCase useCase = new FindClienteByDocumentoUseCase(clienteGateway);

        Cliente cliente = new Cliente(3L, "Carlos", "11122233344");

        when(clienteGateway.findClienteByDocumento("11122233344")).thenReturn(cliente);

        Cliente result = useCase.findClienteByDocumento("11122233344");

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNome(), result.getNome());
        assertEquals(cliente.getDocumento(), result.getDocumento());

        verify(clienteGateway, times(1)).findClienteByDocumento("11122233344");
    }

    @Test
    void shouldReturnNullWhenDocumentoNotFound() {
        FindClienteByDocumentoUseCase useCase = new FindClienteByDocumentoUseCase(clienteGateway);

        when(clienteGateway.findClienteByDocumento("00000000000")).thenReturn(null);

        Cliente result = useCase.findClienteByDocumento("00000000000");

        assertNull(result);
        verify(clienteGateway, times(1)).findClienteByDocumento("00000000000");
    }
}
