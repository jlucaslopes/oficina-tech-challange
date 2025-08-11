package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.*;
import br.com.jlucaslopes.model.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.model.request.ServicoCreateRequest;
import br.com.jlucaslopes.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrdemServiceTest {

    @Mock
    private OrdemServicoRepository ordemRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private PecaRepository pecaRepository;
    @Mock
    private PecaService pecaService;
    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private OrdemService ordemService;

    @Test
    void criarOrdemServicoRetornaOrdemSalva() {
        OrdemServicoCreateRequest request = new OrdemServicoCreateRequest();
        request.setIdCliente("123");
        request.setPlacaVeiculo("ABC1234");
        request.setDescricao("desc");

        Cliente cliente = new Cliente();
        Veiculo veiculo = new Veiculo();

        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.of(cliente));
        Mockito.when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo));
        Mockito.when(ordemRepository.saveAndFlush(Mockito.any(OrdemServico.class))).thenReturn(new OrdemServico());

        OrdemServico result = ordemService.criarOrdemServico(request);

        assertNotNull(result);
    }

    @Test
    void criarOrdemServicoLancaExcecaoQuandoClienteNaoExiste() {
        OrdemServicoCreateRequest request = new OrdemServicoCreateRequest();
        request.setIdCliente("123");
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ordemService.criarOrdemServico(request));
    }

    @Test
    void criarOrdemServicoLancaExcecaoQuandoVeiculoNaoExiste() {
        OrdemServicoCreateRequest request = new OrdemServicoCreateRequest();
        request.setIdCliente("123");
        request.setPlacaVeiculo("ABC1234");
        Mockito.when(clienteRepository.findClienteByDocumento("123")).thenReturn(Optional.of(new Cliente()));
        Mockito.when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ordemService.criarOrdemServico(request));
    }

    @Test
    void buscarOrdemPorIdRetornaOrdemQuandoExiste() {
        OrdemServico ordem = new OrdemServico();
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        OrdemServico result = ordemService.buscarOrdemPorId(1L);

        assertEquals(ordem, result);
    }

    @Test
    void buscarOrdemPorIdLancaExcecaoQuandoNaoExiste() {
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ordemService.buscarOrdemPorId(1L));
    }

    @Test
    void avancarStatusAvancaStatusCorretamente() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.AGUARDANDO_APROVACAO);

        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));
        Mockito.when(ordemRepository.saveAndFlush(Mockito.any())).thenReturn(ordem);

        OrdemServico result = ordemService.avancarStatus(1L);

        assertNotNull(result.getStatus());
    }


    @Test
    void avancarStatusAvancaStatusFinalizada() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.FINALIZADA);

        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));
        Mockito.when(ordemRepository.saveAndFlush(Mockito.any())).thenReturn(ordem);

        OrdemServico ordemServico = ordemService.avancarStatus(1L);
        assertEquals(Status.ENTREGUE,ordemServico.getStatus());
        assertTrue(ordemServico.getDataFim().isAfter(OffsetDateTime.now().minusMinutes(1)));
    }

    @Test
    void avancarStatusAvancaStatusEntregue() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.ENTREGUE);

        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        assertThrows(RuntimeException.class, () -> ordemService.avancarStatus(1L));

    }

    @Test
    void retornarStatusRetornaStatusAnterior() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.AGUARDANDO_APROVACAO);
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));
        Mockito.when(ordemRepository.saveAndFlush(Mockito.any())).thenReturn(ordem);

        OrdemServico result = ordemService.retornarStatus(1L);

        assertNotNull(result.getStatus());
    }

    @Test
    void retornaTempoMedioDeOrdemServicoRetornaMensagemQuandoNaoHaOrdemFinalizada() {
        Mockito.when(ordemRepository.findAllByStatus(Status.ENTREGUE)).thenReturn(Collections.emptyList());

        String result = ordemService.retornaTempoMedioDeOrdemServico();

        assertEquals("Nenhuma ordem de serviço finalizada foi encontrada.", result);
    }

    @Test
    void retornaTempoMedioDeOrdemServicoRetornaTempoFormatado() {
        OrdemServico ordem = new OrdemServico();
        OffsetDateTime fim = OffsetDateTime.now().plusHours(2);
        ordem.setDataFim(fim);
        Mockito.when(ordemRepository.findAllByStatus(Status.ENTREGUE)).thenReturn(List.of(ordem));

        String result = ordemService.retornaTempoMedioDeOrdemServico();

        assertTrue(result.contains("Tempo médio de ordem de serviço:"));
    }

    @Test
    void adicionarServicoAdicionaServicoQuandoStatusNaoValidoESemPeca() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.AGUARDANDO_APROVACAO);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();

        assertThrows(RuntimeException.class, () -> ordemService.adicionarServico(1L, servico));
    }

    @Test
    void adicionarServicoLancaExcecaoQuandoStatusInvalido() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.FINALIZADA);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();

        assertThrows(RuntimeException.class, () -> ordemService.adicionarServico(1L, servico));
    }

    @Test
    void adicionarServicoComPecaLancaExcecaoQuandoEstoqueInsuficiente() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();
        servico.setIdPeca(1l);
        servico.setQuantidade(2L);

        Peca peca = new Peca();
        peca.setDescricao("desc");
        peca.setQuantidadeEstoque(1);

        Mockito.when(pecaRepository.findById(1l)).thenReturn(Optional.of(peca));

        assertThrows(RuntimeException.class, () -> ordemService.adicionarServico(1L, servico));
    }

    @Test
    void adicionarServicoComOrdemEmStatusInvalidoLancaExcecao() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.FINALIZADA);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();

        assertThrows(RuntimeException.class, () -> ordemService.adicionarServico(1L, servico));
    }

    @Test
    void adicionarServicoComOrdemEmStatusValidoComPecaDiferenteDeNull() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);

        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();

        Peca pecaEstoque = new Peca();
        pecaEstoque.setId(1L);
        pecaEstoque.setDescricao("desc");
        pecaEstoque.setQuantidadeEstoque(10);

        assertThrows(RuntimeException.class, () -> ordemService.adicionarServico(1L, servico));
    }

    @Test
    void adicionarServicoComPecaLancaExcecaoQuandoPecaNaoEncontrada() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();
        servico.setIdPeca(1L);
        servico.setQuantidade(1L);

        Mockito.when(pecaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ordemService.adicionarServico(1L, servico));
    }

    @Test
    void adicionarServicoComPecaAtualizaEstoqueEAdicionaServico() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servicoCreateRequest = new ServicoCreateRequest();
        Peca peca = new Peca();
        peca.setId(10L);
        peca.setDescricao("desc");
        peca.setQuantidadeEstoque(5);
        peca.setValorUnitario(10.0);
        servicoCreateRequest.setIdPeca(1L);
        servicoCreateRequest.setQuantidade(2L);

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setPeca(peca);
        servico.setNome("Nome");

        Mockito.when(pecaRepository.findById(anyLong())).thenReturn(Optional.of(peca));
        Mockito.when(ordemRepository.saveAndFlush(Mockito.any())).thenReturn(ordem);
        Mockito.when(servicoRepository.save(Mockito.any())).thenReturn(servico);

        OrdemServico result = ordemService.adicionarServico(1L, servicoCreateRequest);

        Mockito.verify(pecaService).atualizaEstoque(10L, -2);
        assertTrue(ordem.getServicos().contains(servico));
        assertEquals(ordem, result);
    }


    @Test
    void adicionarServicoComPecaAtualizaEstoqueInvalido() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servico = new ServicoCreateRequest();
        Peca peca = new Peca();
        peca.setId(10L);
        peca.setDescricao("desc");
        peca.setQuantidadeEstoque(5);
        servico.setIdPeca(1L);
        servico.setQuantidade(7L);


        Mockito.when(pecaRepository.findById(anyLong())).thenReturn(Optional.of(peca));

        assertThrows((RuntimeException.class), () -> ordemService.adicionarServico(1L, servico));

    }


    @Test
    void adicionarServicoSemPeca() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);
        ordem.setServicos(new ArrayList<>());
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        ServicoCreateRequest servicoCreateRequest = new ServicoCreateRequest();
        servicoCreateRequest.setQuantidade(1L);

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setNome("Nome");

        Mockito.when(ordemRepository.saveAndFlush(Mockito.any())).thenReturn(ordem);
        Mockito.when(servicoRepository.save(Mockito.any())).thenReturn(servico);

        OrdemServico result = ordemService.adicionarServico(1L, servicoCreateRequest);

        assertTrue(ordem.getServicos().contains(servico));
        assertEquals(ordem, result);
    }

    @Test
    void cancelarOrdemCancelaOrdemQuandoStatusValido() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.EM_DIAGNOSTICO);
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));
        Mockito.when(ordemRepository.saveAndFlush(Mockito.any())).thenReturn(ordem);

        OrdemServico result = ordemService.cancelarOrdem(1L);

        assertEquals(Status.CANCELADA, result.getStatus());
    }

    @Test
    void cancelarOrdemCancelaOrdemQuandoStatusInvalido() {
        OrdemServico ordem = new OrdemServico();
        ordem.setStatus(Status.FINALIZADA);
        Mockito.when(ordemRepository.findById(1L)).thenReturn(Optional.of(ordem));

        assertThrows(RuntimeException.class, () -> ordemService.cancelarOrdem(1L));
    }
}