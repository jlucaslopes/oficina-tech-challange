package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.Peca;
import br.com.jlucaslopes.repository.PecaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PecaServiceTest {

    @Mock
    private PecaRepository pecaRepository;

    @InjectMocks
    private PecaService pecaService;

    @Test
    void salvarRetornaPecaSalva() {
        Peca peca = new Peca();
        Mockito.when(pecaRepository.save(peca)).thenReturn(peca);

        Peca result = pecaService.salvar(peca);

        assertEquals(peca, result);
    }

    @Test
    void atualizaEstoqueRetornaPecaAtualizada() {
        Peca peca = new Peca();
        peca.setQuantidadeEstoque(10);
        peca.setId(1L);
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.of(peca));
        Mockito.when(pecaRepository.save(Mockito.any(Peca.class))).thenReturn(peca);

        Peca result = pecaService.atualizaEstoque(1L, -5);

        assertEquals(5, result.getQuantidadeEstoque());
    }

    @Test
    void atualizaEstoqueLancaExcecaoQuandoEstoqueNegativo() {
        Peca peca = new Peca();
        peca.setQuantidadeEstoque(2);
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.of(peca));

        assertThrows(RuntimeException.class, () -> pecaService.atualizaEstoque(1L, -5));
    }

    @Test
    void atualizaEstoqueLancaExcecaoQuandoPecaNaoExiste() {
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pecaService.atualizaEstoque(1L, 1));
    }

    @Test
    void buscarPorIdRetornaPecaQuandoExiste() {
        Peca peca = new Peca();
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.of(peca));

        Peca result = pecaService.buscarPorId(1L);

        assertEquals(peca, result);
    }

    @Test
    void buscarPorIdLancaExcecaoQuandoNaoExiste() {
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pecaService.buscarPorId(1L));
    }

    @Test
    void atualizarRetornaPecaAtualizada() {
        Peca peca = new Peca();
        peca.setDescricao("desc antiga");
        Peca pecaAtualizada = new Peca();
        pecaAtualizada.setDescricao("nova desc");
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.of(peca));
        Mockito.when(pecaRepository.save(Mockito.any(Peca.class))).thenReturn(pecaAtualizada);

        Peca result = pecaService.atualizar(1L, pecaAtualizada);

        assertEquals("nova desc", result.getDescricao());
    }

    @Test
    void atualizarLancaExcecaoQuandoNaoExiste() {
        Peca pecaAtualizada = new Peca();
        Mockito.when(pecaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pecaService.atualizar(1L, pecaAtualizada));
    }

    @Test
    void deletarChamaRepository() {
        pecaService.deletar(1L);
        Mockito.verify(pecaRepository).deleteById(1L);
    }
}