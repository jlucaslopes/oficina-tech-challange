package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.application.usecases.cliente.DeleteClienteByIdUseCase;
import br.com.jlucaslopes.application.usecases.cliente.FindClienteByDocumentoUseCase;
import br.com.jlucaslopes.application.usecases.cliente.FindClienteByIdUseCase;
import br.com.jlucaslopes.application.usecases.cliente.SaveClienteUseCase;
import br.com.jlucaslopes.domain.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteGateway clienteGateway;

    private DeleteClienteByIdUseCase deleteClienteByIdUseCase;
    private FindClienteByDocumentoUseCase findClienteByDocumentoUseCase;
    private FindClienteByIdUseCase findClienteByIdUseCase;
    private SaveClienteUseCase saveClienteUseCase;

    private ClienteController controller;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        // create real use case instances wired to the mocked gateway to avoid mocking concrete classes
        this.deleteClienteByIdUseCase = new DeleteClienteByIdUseCase(clienteGateway);
        this.findClienteByDocumentoUseCase = new FindClienteByDocumentoUseCase(clienteGateway);
        this.findClienteByIdUseCase = new FindClienteByIdUseCase(clienteGateway);
        this.saveClienteUseCase = new SaveClienteUseCase(clienteGateway);

        this.controller = new ClienteController(
                deleteClienteByIdUseCase,
                findClienteByDocumentoUseCase,
                findClienteByIdUseCase,
                saveClienteUseCase
        );
    }

    @Test
    void getClienteByIdShouldReturnCliente() {
        Cliente cliente = new Cliente(1L, "Nome", "12345678901");

    when(clienteGateway.findClienteById(1)).thenReturn(cliente);

        ResponseEntity<Cliente> response = controller.getClienteById("1");

        assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
        assertEquals(cliente, response.getBody());

    verify(clienteGateway, times(1)).findClienteById(1);
    }

    @Test
    void getClienteByDocumentoShouldReturnCliente() {
        Cliente cliente = new Cliente(2L, "Outro", "98765432100");

    when(clienteGateway.findClienteByDocumento("98765432100")).thenReturn(cliente);

        ResponseEntity<Cliente> response = controller.getClienteByDocumento("98765432100");

        assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
        assertEquals(cliente, response.getBody());

    verify(clienteGateway, times(1)).findClienteByDocumento("98765432100");
    }

    @Test
    void deleteClienteByIdShouldCallUseCaseAndReturnNoContent() {
    doNothing().when(clienteGateway).deleteById(3);

        ResponseEntity<Void> response = controller.deleteClienteById("3");

        assertNotNull(response);
    assertEquals(204, response.getStatusCode().value());

    verify(clienteGateway, times(1)).deleteById(3);
    }

    @Test
    void createClienteShouldSaveAndReturnCliente() {
        Cliente input = new Cliente("11122233344", "Novo Cliente");
        Cliente saved = new Cliente(4L, "Novo Cliente", "11122233344");

    when(clienteGateway.save(input)).thenReturn(saved);

        ResponseEntity<Cliente> response = controller.createCliente(input);

        assertNotNull(response);
    assertEquals(200, response.getStatusCode().value());
        assertEquals(saved, response.getBody());

    verify(clienteGateway, times(1)).save(input);
    }
}
