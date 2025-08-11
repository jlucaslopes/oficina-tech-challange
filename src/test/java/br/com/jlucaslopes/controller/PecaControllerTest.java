package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.Peca;
import br.com.jlucaslopes.service.PecaService;
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
class PecaControllerTest {

    @Mock
    private PecaService pecaService;

    @InjectMocks
    private PecaController pecaController;

    @Test
    void salvarRetornaPecaSalva() {
        Peca peca = new Peca();
        Mockito.when(pecaService.salvar(peca)).thenReturn(peca);

        ResponseEntity<Peca> response = pecaController.salvar(peca);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peca, response.getBody());
    }

    @Test
    void buscarPorIdRetornaPecaQuandoIdValido() {
        Peca peca = new Peca();
        Mockito.when(pecaService.buscarPorId(1L)).thenReturn(peca);

        ResponseEntity<Peca> response = pecaController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peca, response.getBody());
    }

    @Test
    void atualizarRetornaPecaAtualizada() {
        Peca pecaAtualizada = new Peca();
        Mockito.when(pecaService.atualizar(1L, pecaAtualizada)).thenReturn(pecaAtualizada);

        ResponseEntity<Peca> response = pecaController.atualizar(1L, pecaAtualizada);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pecaAtualizada, response.getBody());
    }

    @Test
    void deletarRetornaNoContent() {
        Mockito.doNothing().when(pecaService).deletar(1L);

        ResponseEntity response = pecaController.deletar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void atualizaEstoqueRetornaPecaAtualizada() {
        Peca peca = new Peca();
        Mockito.when(pecaService.atualizaEstoque(1L, 5)).thenReturn(peca);

        ResponseEntity<Peca> response = pecaController.atualizaEstoque(1L, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(peca, response.getBody());
    }
}