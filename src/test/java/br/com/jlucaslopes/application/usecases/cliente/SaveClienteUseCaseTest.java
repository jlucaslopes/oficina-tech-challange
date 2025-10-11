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
class SaveClienteUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    void shouldSaveCliente() {
        SaveClienteUseCase useCase = new SaveClienteUseCase(clienteGateway);

        Cliente input = new Cliente("12345678901", "João Silva");
        Cliente saved = new Cliente(1L, "João Silva", "12345678901");

        when(clienteGateway.save(input)).thenReturn(saved);

        Cliente result = useCase.save(input);

        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getNome(), result.getNome());
        assertEquals(saved.getDocumento(), result.getDocumento());

        verify(clienteGateway, times(1)).save(input);
    }
}
