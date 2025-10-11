package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.usecases.ordemservico.*;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdemControllerTest {

    @Mock
    private br.com.jlucaslopes.application.gateways.OrdemServicoGateway gateway;

    private OrdemController controller;

    @BeforeEach
    void setUp() {
        var adicionarServicoUseCase = new AdicionarServicoUseCase(gateway);
        var avancarStatusUseCase = new AvancarStatusUseCase(gateway);
        var buscarOrdemPorIdUseCase = new BuscaOrdemPorIdUseCase(gateway);
        var cancelarOrdemUseCase = new CancelarOrdemUseCase(gateway);
        var criaOrdemServicoUseCase = new CriaOrdemServicoUseCase(gateway);
        var retornarStatusUseCase = new RetornarStatusUseCase(gateway);
        var retornaTempoMedioUseCase = new RetornaTempoMedioUseCase(gateway);
        var consultarStatusUseCase = new ConsultarStatusUseCase(gateway);
        var aprovarOrcamentoUseCase = new AprovarOrcamentoUseCase(gateway);
        var listarOrdensUseCase = new ListarOrdensUseCase(gateway);

        controller = new OrdemController(adicionarServicoUseCase,
                avancarStatusUseCase,
                buscarOrdemPorIdUseCase,
                cancelarOrdemUseCase,
                criaOrdemServicoUseCase,
                retornarStatusUseCase,
                retornaTempoMedioUseCase,
                consultarStatusUseCase,
                aprovarOrcamentoUseCase,
                listarOrdensUseCase);
    }

    @Test
    void criarOrdem_delegatesToUseCase_andReturnsOrdem() {
        OrdemServicoCreateRequest req = new OrdemServicoCreateRequest();
        req.setDescricao("desc");
        req.setIdCliente("1");
        req.setPlacaVeiculo("ABC-1234");

        OrdemServico expected = new OrdemServico();
        expected.setId(10L);

    when(gateway.criarOrdemServico(eq(req))).thenReturn(expected);

    OrdemServico result = controller.criarOrdem(req);

        assertSame(expected, result);
    }

    @Test
    void buscarPorId_returnsOrdemFromUseCase() {
        OrdemServico expected = new OrdemServico();
        expected.setId(5L);

    when(gateway.buscarOrdemPorId(5L)).thenReturn(expected);

    OrdemServico result = controller.buscarPorId(5L);

        assertSame(expected, result);
    }

    @Test
    void adicionarServico_delegatesAndReturnsOrdem() {
        ServicoCreateRequest serv = new ServicoCreateRequest();
        serv.setNome("troca de oleo");
        OrdemServico expected = new OrdemServico();
        expected.setId(7L);

    when(gateway.adicionarServico(3L, serv)).thenReturn(expected);

    OrdemServico result = controller.adicionarServico(3L, serv);

        assertSame(expected, result);
    }

    @Test
    void avancarStatus_delegatesAndReturnsOrdem() {
        OrdemServico expected = new OrdemServico();
        expected.setId(8L);

    when(gateway.avancarStatus(8L)).thenReturn(expected);

    OrdemServico result = controller.avancarStatus(8L);

        assertSame(expected, result);
    }

    @Test
    void retornarStatus_delegatesAndReturnsOrdem() {
        OrdemServico expected = new OrdemServico();
        expected.setId(9L);

    when(gateway.retornarStatus(9L)).thenReturn(expected);

    OrdemServico result = controller.retornarStatus(9L);

        assertSame(expected, result);
    }

    @Test
    void consultarStatus_returnsStringFromUseCase() {
    when(gateway.consultarStatus(15L)).thenReturn("EM_ANDAMENTO");

    String status = controller.consultarStatus(15L);

        assertEquals("EM_ANDAMENTO", status);
    }

    @Test
    void cancelarOrdem_delegatesAndReturnsOrdem() {
        OrdemServico expected = new OrdemServico();
        expected.setId(11L);

    when(gateway.cancelarOrdem(11L)).thenReturn(expected);

    OrdemServico result = controller.cancelarOrdem(11L);

        assertSame(expected, result);
    }

    @Test
    void aprovarOrcamento_callsUseCase_andReturnsNoContent() {
    doNothing().when(gateway).aprovaOrcamento(20L, true);

    ResponseEntity<Object> resp = controller.aprovarOrcamento(20L, true);

    assertEquals(204, resp.getStatusCode().value());
    }
}
