package br.com.jlucaslopes.controller;


import br.com.jlucaslopes.model.Veiculo;
import br.com.jlucaslopes.model.request.VeiculoCreateRequest;
import br.com.jlucaslopes.service.VeiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VeiculoControllerTest {

    @Mock
    private VeiculoService veiculoService;

    @InjectMocks
    private VeiculoController veiculoController;

    @Test
    void salvarRetornaVeiculoSalvo() {
        VeiculoCreateRequest request = new VeiculoCreateRequest();
        Veiculo veiculo = new Veiculo();
        Mockito.when(veiculoService.salvar(request)).thenReturn(veiculo);

        Veiculo result = veiculoController.salvar(request);

        assertEquals(veiculo, result);
    }

    @Test
    void buscarPorIdRetornaVeiculoQuandoIdValido() {
        Veiculo veiculo = new Veiculo();
        Mockito.when(veiculoService.buscarPorId(1L)).thenReturn(veiculo);

        Veiculo result = veiculoController.buscarPorId(1L);

        assertEquals(veiculo, result);
    }

    @Test
    void buscarPorPlacaRetornaVeiculoQuandoPlacaValida() {
        Veiculo veiculo = new Veiculo();
        Mockito.when(veiculoService.findVeiculoByPlaca("ABC1234")).thenReturn(veiculo);

        ResponseEntity<Veiculo> response = veiculoController.buscarPorPlaca("ABC1234");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
    }

    @Test
    void atualizarRetornaVeiculoAtualizado() {
        Veiculo veiculoAtualizado = new Veiculo();
        Mockito.when(veiculoService.atualizar(1L, veiculoAtualizado)).thenReturn(veiculoAtualizado);

        Veiculo result = veiculoController.atualizar(1L, veiculoAtualizado);

        assertEquals(veiculoAtualizado, result);
    }

    @Test
    void deletarNaoLancaExcecaoQuandoIdValido() {
        Mockito.doNothing().when(veiculoService).deletar(1L);

        assertDoesNotThrow(() -> veiculoController.deletar(1L));
    }

    @Test
    void findVeiculosByClienteDocumentoRetornaListaDeVeiculos() {
        List<Veiculo> veiculos = Collections.singletonList(new Veiculo());
        Mockito.when(veiculoService.findVeiculosByClienteDocumento("123456789")).thenReturn(veiculos);

        List<Veiculo> result = veiculoController.findVeiculosByClienteDocumento("123456789");

        assertEquals(veiculos, result);
    }
}