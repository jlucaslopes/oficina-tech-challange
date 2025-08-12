package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.model.exception.ClienteJaExisteException;
import br.com.jlucaslopes.model.exception.ClienteNaoEncontradoException;
import br.com.jlucaslopes.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void findClienteByIdRetornaClienteQuandoExiste() {
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findClienteById(1);

        assertEquals(cliente, result);
    }

    @Test
    void findClienteByIdLancaExcecaoQuandoNaoExiste() {
        Mockito.when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clienteService.findClienteById(1));
    }

    @Test
    void saveRetornaClienteSalvo() {
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.save(cliente);

        assertEquals(cliente, result);
    }

    @Test
    void deleteByIdChamaRepository() {
        clienteService.deleteById(1);
        Mockito.verify(clienteRepository).deleteById(1);
    }

    @Test
    void findClienteByDocumentoRetornaClienteQuandoExiste() {
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findClienteByDocumento("123");

        assertEquals(cliente, result);
    }

    @Test
    void findClienteByDocumentoLancaExcecaoQuandoNaoExiste() {
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.findClienteByDocumento("123"));
    }

    @Test
    void salvarClienteJaExistenteRetornaException() {
        Mockito.when(clienteRepository.findClienteByDocumento(anyString())).thenReturn(Optional.of(new Cliente()));
        Cliente cliente = new Cliente("nome", "74148766084" );
        assertThrows(ClienteJaExisteException.class, () -> clienteService.save(cliente));
    }
}