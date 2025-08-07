package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("findClienteByDocumento retorna cliente quando documento existe")
    void findClienteByDocumentoRetornaClienteQuandoExiste() {
        Cliente cliente = new Cliente();
        cliente.setDocumento("123456789");
        cliente.setNome("João");
        clienteRepository.save(cliente);

        Optional<Cliente> result = clienteRepository.findClienteByDocumento("123456789");

        assertTrue(result.isPresent());
        assertEquals("João", result.get().getNome());
    }

    @Test
    @DisplayName("findClienteByDocumento retorna vazio quando documento não existe")
    void findClienteByDocumentoRetornaVazioQuandoNaoExiste() {
        Optional<Cliente> result = clienteRepository.findClienteByDocumento("000000000");
        assertFalse(result.isPresent());
    }
}