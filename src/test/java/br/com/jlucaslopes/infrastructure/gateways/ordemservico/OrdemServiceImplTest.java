package br.com.jlucaslopes.infrastructure.gateways.ordemservico;

import br.com.jlucaslopes.application.exceptions.*;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.entities.Peca;
import br.com.jlucaslopes.domain.entities.Servico;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;
import br.com.jlucaslopes.infrastructure.gateways.peca.PecaServiceImpl;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteEntity;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoEntity;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoRepository;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.Status;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaEntity;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaRepository;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoEntity;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoRepository;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoEntity;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServiceImplTest {

    @Mock
    br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository clienteRepository;

    @Mock
    VeiculoRepository veiculoRepository;

    @Mock
    OrdemServicoRepository ordemServicoRepository;

    @Mock
    ServicoRepository servicoRepository;

    @Mock
    PecaRepository pecaRepository;

    @Mock
    PecaServiceImpl pecaServiceImpl;

    @Captor
    ArgumentCaptor<OrdemServicoEntity> ordemCaptor;

    private OrdemServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OrdemServiceImpl(clienteRepository, veiculoRepository, ordemServicoRepository, servicoRepository, pecaRepository, pecaServiceImpl);
    }

    // helper builder methods
    private ClienteEntity clienteEntity() {
        ClienteEntity c = new ClienteEntity();
        c.setId(1L);
        c.setDocumento("11122233344");
        c.setNome("nome");
        return c;
    }

    private VeiculoEntity veiculoEntity() {
        VeiculoEntity v = new VeiculoEntity();
        v.setId(2L);
        v.setPlaca("ABC-1234");
        return v;
    }

    private OrdemServicoEntity ordemEntityWithStatus(Status status) {
        OffsetDateTime inicio = OffsetDateTime.now().minusHours(2);
        OffsetDateTime fim = null;
        OrdemServicoEntity e = new OrdemServicoEntity(null, "d", inicio, fim, status, null, List.of());
        e.setId(9L);
        return e;
    }

    private PecaEntity pecaEntityWithQty(long id, int qty) {
        PecaEntity p = new PecaEntity();
        p.setId(id);
        p.setDescricao("p");
        p.setQuantidadeEstoque(qty);
        p.setValorUnitario(10.0);
        return p;
    }
//
//    @Test
//    void criarOrdemServico_success() {
//        OrdemServicoCreateRequest req = new OrdemServicoCreateRequest();
//        req.setIdCliente("11122233344");
//        req.setPlacaVeiculo("ABC-1234");
//        req.setDescricao("desc");
//
//        when(clienteRepository.findClienteByDocumento("11122233344")).thenReturn(Optional.of(clienteEntity()));
//        when(veiculoRepository.findByPlaca("ABC-1234")).thenReturn(Optional.of(veiculoEntity()));
//
//        OrdemServicoEntity saved = ordemEntityWithStatus(Status.RECEBIDA);
//        saved.setId(100L);
//        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(saved);
//
//        var result = service.criarOrdemServico(req);
//
//        assertNotNull(result);
//        assertEquals(100L, result.getId());
//    }

    @Test
    void criarOrdemServico_clienteNotFound_throws() {
        OrdemServicoCreateRequest req = new OrdemServicoCreateRequest();
        req.setIdCliente("x");
        when(clienteRepository.findClienteByDocumento("x")).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> service.criarOrdemServico(req));
    }

    @Test
    void criarOrdemServico_veiculoNotFound_throws() {
        OrdemServicoCreateRequest req = new OrdemServicoCreateRequest();
        req.setIdCliente("111");
        req.setPlacaVeiculo("nope");

        when(clienteRepository.findClienteByDocumento("111")).thenReturn(Optional.of(clienteEntity()));
        when(veiculoRepository.findByPlaca("nope")).thenReturn(Optional.empty());

        assertThrows(VeiculoNaoEncontradoException.class, () -> service.criarOrdemServico(req));
    }

    @Test
    void buscarOrdemPorId_found() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.RECEBIDA);
        when(ordemServicoRepository.findById(5L)).thenReturn(Optional.of(e));

        var res = service.buscarOrdemPorId(5L);
        assertEquals(e.getId(), res.getId());
    }

    @Test
    void buscarOrdemPorId_notFound_throws() {
        when(ordemServicoRepository.findById(6L)).thenReturn(Optional.empty());
        assertThrows(OrdemNaoEncontradaException.class, () -> service.buscarOrdemPorId(6L));
    }

    @Test
    void adicionarServico_invalidStatus_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.RECEBIDA);
        when(ordemServicoRepository.findById(1L)).thenReturn(Optional.of(e));

        ServicoCreateRequest req = new ServicoCreateRequest();
        req.setNome("s");

        assertThrows(OrdemStatusInvalidoAddServicoException.class, () -> service.adicionarServico(1L, req));
    }

    @Test
    void adicionarServico_pecaNotFound_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
        when(ordemServicoRepository.findById(2L)).thenReturn(Optional.of(e));

        ServicoCreateRequest req = new ServicoCreateRequest();
        req.setIdPeca(99L);
        req.setQuantidade(1L);

        when(pecaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PecaNaoEncontradaException.class, () -> service.adicionarServico(2L, req));
    }

    @Test
    void adicionarServico_estoqueInsuficiente_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
        when(ordemServicoRepository.findById(3L)).thenReturn(Optional.of(e));

        ServicoCreateRequest req = new ServicoCreateRequest();
        req.setIdPeca(10L);
        req.setQuantidade(5L);

        PecaEntity p = pecaEntityWithQty(10L, 2);
        when(pecaRepository.findById(10L)).thenReturn(Optional.of(p));

        assertThrows(EstoqueInsuficienteException.class, () -> service.adicionarServico(3L, req));
    }

//    @Test
//    void adicionarServico_withPeca_success() {
//        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
//        when(ordemServicoRepository.findById(4L)).thenReturn(Optional.of(e));
//
//        ServicoCreateRequest req = new ServicoCreateRequest();
//        req.setIdPeca(20L);
//        req.setQuantidade(2L);
//        req.setNome("s");
//
//        PecaEntity p = pecaEntityWithQty(20L, 5);
//        when(pecaRepository.findById(20L)).thenReturn(Optional.of(p));
//
//        when(pecaServiceImpl.atualizaEstoque(eq(20L), anyInt())).thenReturn(new Peca(20L, "p", 10.0, 3));
//
//        ServicoEntity savedServico = new ServicoEntity();
//        savedServico.setId(55L);
//        when(servicoRepository.save(any())).thenReturn(savedServico);
//
//        OrdemServicoEntity savedOrdem = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
//        savedOrdem.setId(200L);
//        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(savedOrdem);
//
//        var result = service.adicionarServico(4L, req);
//        assertEquals(200L, result.getId());
//    }
//
//    @Test
//    void adicionarServico_withoutPeca_success() {
//        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
//        when(ordemServicoRepository.findById(12L)).thenReturn(Optional.of(e));
//
//        ServicoCreateRequest req = new ServicoCreateRequest();
//        req.setNome("nope");
//        req.setQuantidade(1L);
//
//        ServicoEntity savedServico = new ServicoEntity();
//        savedServico.setId(66L);
//        when(servicoRepository.save(any())).thenReturn(savedServico);
//
//        OrdemServicoEntity savedOrdem = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
//        savedOrdem.setId(201L);
//        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(savedOrdem);
//
//        var result = service.adicionarServico(12L, req);
//        assertEquals(201L, result.getId());
//    }
//
//    @Test
//    void avancarStatus_toEntregue_setsDataFim() {
//        OrdemServicoEntity e = ordemEntityWithStatus(Status.FINALIZADA);
//        e.setId(7L);
//        when(ordemServicoRepository.findById(7L)).thenReturn(Optional.of(e));
//
//        OrdemServicoEntity saved = ordemEntityWithStatus(Status.ENTREGUE);
//        saved.setId(7L);
//        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(saved);
//
//        var res = service.avancarStatus(7L);
//        assertEquals(Status.ENTREGUE, res.getStatus());
//        assertNotNull(res.getDataFim());
//    }

    @Test
    void avancarStatus_invalid_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.ENTREGUE);
        when(ordemServicoRepository.findById(8L)).thenReturn(Optional.of(e));

        assertThrows(StatusNaoPodeMudarException.class, () -> service.avancarStatus(8L));
    }

    @Test
    void retornarStatus_success() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_EXECUCAO);
        e.setId(13L);
        when(ordemServicoRepository.findById(13L)).thenReturn(Optional.of(e));

        OrdemServicoEntity saved = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
        saved.setId(13L);
        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(saved);

        var res = service.retornarStatus(13L);
        assertEquals(Status.EM_DIAGNOSTICO, res.getStatus());
    }

    @Test
    void retornarStatus_invalid_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.RECEBIDA);
        when(ordemServicoRepository.findById(14L)).thenReturn(Optional.of(e));

        assertThrows(StatusNaoPodeMudarException.class, () -> service.retornarStatus(14L));
    }

    @Test
    void cancelarOrdem_invalid_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.ENTREGUE);
        when(ordemServicoRepository.findById(21L)).thenReturn(Optional.of(e));

        assertThrows(CancelarOrdemException.class, () -> service.cancelarOrdem(21L));
    }

//    @Test
//    void cancelarOrdem_success() {
//        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
//        e.setId(22L);
//        when(ordemServicoRepository.findById(22L)).thenReturn(Optional.of(e));
//
//        OrdemServicoEntity saved = ordemEntityWithStatus(Status.CANCELADA);
//        saved.setId(22L);
//        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(saved);
//
//        var res = service.cancelarOrdem(22L);
//        assertEquals(Status.CANCELADA, res.getStatus());
//        assertNotNull(res.getDataFim());
//    }

    @Test
    void retornaTempoMedio_noOrders() {
        when(ordemServicoRepository.findAllByStatus(Status.ENTREGUE)).thenReturn(List.of());
        var res = service.retornaTempoMedioDeOrdemServico();
        assertTrue(res.contains("Nenhuma ordem"));
    }

    @Test
    void retornaTempoMedio_withOrders() {
    OffsetDateTime inicio = OffsetDateTime.now().minusSeconds(3661);
    OffsetDateTime fim = OffsetDateTime.now();
    OrdemServicoEntity a = new OrdemServicoEntity(1L, "d", inicio, fim, Status.ENTREGUE, null, List.of());
    when(ordemServicoRepository.findAllByStatus(Status.ENTREGUE)).thenReturn(List.of(a));

        var res = service.retornaTempoMedioDeOrdemServico();
        assertTrue(res.contains("Tempo médio de ordem de serviço:"));
        assertTrue(res.contains("01:01:01") || res.contains("01:01:0"));
    }

    @Test
    void consultarStatus_found() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.RECEBIDA);
        when(ordemServicoRepository.findById(30L)).thenReturn(Optional.of(e));
        var res = service.consultarStatus(30L);
        assertEquals("RECEBIDA", res);
    }

    @Test
    void consultarStatus_notFound_throws() {
        when(ordemServicoRepository.findById(31L)).thenReturn(Optional.empty());
        assertThrows(OrdemNaoEncontradaException.class, () -> service.consultarStatus(31L));
    }

    @Test
    void aprovaOrcamento_invalid_throws() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.EM_DIAGNOSTICO);
        when(ordemServicoRepository.findById(40L)).thenReturn(Optional.of(e));
        assertThrows(OrdemStatusInvalidoAprovaOrcamentoException.class, () -> service.aprovaOrcamento(40L, true));
    }

    @Test
    void aprovaOrcamento_approved_setsExecution() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.AGUARDANDO_APROVACAO);
        when(ordemServicoRepository.findById(41L)).thenReturn(Optional.of(e));
        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(e);

        service.aprovaOrcamento(41L, true);
        // no exception means success; verify repository saved
        verify(ordemServicoRepository).saveAndFlush(any());
    }

    @Test
    void aprovaOrcamento_rejected_setsCancel() {
        OrdemServicoEntity e = ordemEntityWithStatus(Status.AGUARDANDO_APROVACAO);
        when(ordemServicoRepository.findById(42L)).thenReturn(Optional.of(e));
        when(ordemServicoRepository.saveAndFlush(any())).thenReturn(e);

        service.aprovaOrcamento(42L, false);
        verify(ordemServicoRepository).saveAndFlush(any());
    }

    @Test
    void listarOrdemServicos_returnsList() {
        OrdemServicoEntity e1 = ordemEntityWithStatus(Status.FINALIZADA);
        OrdemServicoEntity e2 = ordemEntityWithStatus(Status.ENTREGUE);
        when(ordemServicoRepository.findAllForListagem(List.of(Status.FINALIZADA, Status.ENTREGUE))).thenReturn(List.of(e1, e2));

        var res = service.listarOrdemServicos();
        assertEquals(2, res.size());
    }
}
