package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void getClienteByIdReturnsClienteWhenIdIsValid() {
        Cliente cliente = new Cliente( "John Doe", "123456789");
        Mockito.when(clienteService.findClienteById(1)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.getClienteById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void getClienteByIdThrowsExceptionWhenIdIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> clienteController.getClienteById("invalid"));
    }

    @Test
    void getClienteByDocumentoReturnsClienteWhenDocumentoIsValid() {
        Cliente cliente = new Cliente( "John Doe", "123456789");
        Mockito.when(clienteService.findClienteByDocumento("123456789")).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.getClienteByDocumento("123456789");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void deleteClienteByIdReturnsNoContentWhenIdIsValid() {
        Mockito.doNothing().when(clienteService).deleteById(1);

        ResponseEntity<Void> response = clienteController.deleteClienteById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void createClienteReturnsSavedCliente() {
        Cliente cliente = new Cliente( "John Doe", "123456789");
        Cliente savedCliente = new Cliente("John Doe", "123456789");
        Mockito.when(clienteService.save(cliente)).thenReturn(savedCliente);

        ResponseEntity<Cliente> response = clienteController.createCliente(cliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedCliente, response.getBody());
    }
}