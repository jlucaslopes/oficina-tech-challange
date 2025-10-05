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
class FindClienteByIdUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    void shouldReturnClienteWhenFound() {
        FindClienteByIdUseCase useCase = new FindClienteByIdUseCase(clienteGateway);

        Cliente cliente = new Cliente(2L, "Maria", "98765432100");

        when(clienteGateway.findClienteById(2)).thenReturn(cliente);

        Cliente result = useCase.findClienteById(2);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNome(), result.getNome());
        assertEquals(cliente.getDocumento(), result.getDocumento());

        verify(clienteGateway, times(1)).findClienteById(2);
    }

    @Test
    void shouldReturnNullWhenNotFound() {
        FindClienteByIdUseCase useCase = new FindClienteByIdUseCase(clienteGateway);

        when(clienteGateway.findClienteById(100)).thenReturn(null);

        Cliente result = useCase.findClienteById(100);

        assertNull(result);
        verify(clienteGateway, times(1)).findClienteById(100);
    }
}
