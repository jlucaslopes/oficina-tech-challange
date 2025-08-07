package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.model.Veiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("findByPlaca retorna veiculo quando placa existe")
    void findByPlacaRetornaVeiculoQuandoExiste() {
        Cliente cliente = new Cliente();
        cliente.setDocumento("123");
        cliente.setNome("Maria");
        clienteRepository.save(cliente);

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setCliente(cliente);
        veiculoRepository.save(veiculo);

        Optional<Veiculo> result = veiculoRepository.findByPlaca("ABC1234");

        assertTrue(result.isPresent());
        assertEquals("ABC1234", result.get().getPlaca());
    }

    @Test
    @DisplayName("findByPlaca retorna vazio quando placa não existe")
    void findByPlacaRetornaVazioQuandoNaoExiste() {
        Optional<Veiculo> result = veiculoRepository.findByPlaca("ZZZ9999");
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("findByClienteDocumento retorna lista de veiculos do cliente")
    void findByClienteDocumentoRetornaLista() {
        Cliente cliente = new Cliente();
        cliente.setDocumento("456");
        cliente.setNome("Carlos");
        clienteRepository.save(cliente);

        Veiculo veiculo1 = new Veiculo();
        veiculo1.setPlaca("DEF5678");
        veiculo1.setCliente(cliente);
        veiculoRepository.save(veiculo1);

        List<Veiculo> result = veiculoRepository.findByClienteDocumento("456");

        assertEquals(1, result.size());
        assertEquals("DEF5678", result.get(0).getPlaca());
    }
}