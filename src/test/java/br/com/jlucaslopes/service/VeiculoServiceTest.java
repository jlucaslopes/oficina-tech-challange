package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.model.Veiculo;
import br.com.jlucaslopes.model.request.VeiculoCreateRequest;
import br.com.jlucaslopes.repository.ClienteRepository;
import br.com.jlucaslopes.repository.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @Test
    void salvarRetornaVeiculoSalvo() {
        VeiculoCreateRequest request = new VeiculoCreateRequest();
        request.setClienteDocumento("123");
        request.setPlaca("ABC1234");
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.of(cliente));
        Mockito.when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.empty());
        Mockito.when(veiculoRepository.save(Mockito.any(Veiculo.class))).thenReturn(new Veiculo());

        Veiculo result = veiculoService.salvar(request);

        assertNotNull(result);
    }

    @Test
    void salvarLancaExcecaoQuandoClienteNaoExiste() {
        VeiculoCreateRequest request = new VeiculoCreateRequest();
        request.setClienteDocumento("123");
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> veiculoService.salvar(request));
    }

    @Test
    void salvarLancaExcecaoQuandoPlacaJaCadastrada() {
        VeiculoCreateRequest request = new VeiculoCreateRequest();
        request.setClienteDocumento("123");
        request.setPlaca("ABC1234");
        Cliente cliente = new Cliente();
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.of(cliente));
        Mockito.when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(new Veiculo()));

        assertThrows(RuntimeException.class, () -> veiculoService.salvar(request));
    }

    @Test
    void buscarPorIdRetornaVeiculoQuandoExiste() {
        Veiculo veiculo = new Veiculo();
        Mockito.when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        Veiculo result = veiculoService.buscarPorId(1L);

        assertEquals(veiculo, result);
    }

    @Test
    void buscarPorIdLancaExcecaoQuandoNaoExiste() {
        Mockito.when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> veiculoService.buscarPorId(1L));
    }

    @Test
    void atualizarRetornaVeiculoAtualizado() {
        Veiculo veiculoAtualizado = new Veiculo();
        Mockito.when(veiculoRepository.findById(anyLong())).thenReturn(Optional.of(new Veiculo()));
        Mockito.when(veiculoRepository.save(Mockito.any(Veiculo.class))).thenReturn(veiculoAtualizado);

        Veiculo result = veiculoService.atualizar(1L, veiculoAtualizado);

        assertEquals(veiculoAtualizado, result);
    }

    @Test
    void atualizarLancaExcecaoQuandoNaoExiste() {
        Veiculo veiculoAtualizado = new Veiculo();
        Mockito.when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> veiculoService.atualizar(1L, veiculoAtualizado));
    }

    @Test
    void deletarChamaRepository() {
        veiculoService.deletar(1L);
        Mockito.verify(veiculoRepository).deleteById(1L);
    }

    @Test
    void findVeiculosByClienteDocumentoRetornaLista() {
        Mockito.when(veiculoRepository.findByClienteDocumento("123")).thenReturn(Collections.singletonList(new Veiculo()));

        assertEquals(1, veiculoService.findVeiculosByClienteDocumento("123").size());
    }

    @Test
    void findVeiculoByPlacaRetornaVeiculoQuandoExiste() {
        Veiculo veiculo = new Veiculo();
        Mockito.when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo));

        Veiculo result = veiculoService.findVeiculoByPlaca("ABC1234");

        assertEquals(veiculo, result);
    }

    @Test
    void findVeiculoByPlacaLancaExcecaoQuandoNaoExiste() {
        Mockito.when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> veiculoService.findVeiculoByPlaca("ABC1234"));
    }
}