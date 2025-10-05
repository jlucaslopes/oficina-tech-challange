package br.com.jlucaslopes.application.usecases.cliente;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClienteByIdUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    void shouldCallDeleteOnGateway() {
        DeleteClienteByIdUseCase useCase = new DeleteClienteByIdUseCase(clienteGateway);

        useCase.deleteById(5);

        verify(clienteGateway, times(1)).deleteById(5);
    }
}
