package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.model.OrdemServico;
import br.com.jlucaslopes.model.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.model.request.ServicoCreateRequest;
import br.com.jlucaslopes.service.OrdemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrdemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrdemService ordemService;

    @InjectMocks
    private OrdemController ordemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ordemController).build();
    }

    @Test
    @DisplayName("Deve criar uma ordem com sucesso")
    void criarOrdemComSucesso() throws Exception {
        OrdemServico ordemServico = new OrdemServico();

        when(ordemService.criarOrdemServico(any(OrdemServicoCreateRequest.class))).thenReturn(ordemServico);

        mockMvc.perform(post("/ordens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(ordemService, times(1)).criarOrdemServico(any(OrdemServicoCreateRequest.class));
    }

    @Test
    @DisplayName("Deve retornar uma ordem pelo ID")
    void buscarOrdemPorId() throws Exception {
        OrdemServico ordemServico = new OrdemServico();
        when(ordemService.buscarOrdemPorId(1L)).thenReturn(ordemServico);

        mockMvc.perform(get("/ordens/1"))
                .andExpect(status().isOk());

        verify(ordemService, times(1)).buscarOrdemPorId(1L);
    }

    @Test
    @DisplayName("Deve adicionar um serviço a uma ordem")
    void adicionarServico() throws Exception {
        OrdemServico ordemServico = new OrdemServico();

        when(ordemService.adicionarServico(eq(1L), any(ServicoCreateRequest.class))).thenReturn(ordemServico);

        mockMvc.perform(post("/ordens/1/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(ordemService, times(1)).adicionarServico(eq(1L), any(ServicoCreateRequest.class));
    }

    @Test
    @DisplayName("Deve avançar o status de uma ordem")
    void avancarStatus() throws Exception {
        OrdemServico ordemServico = new OrdemServico();

        when(ordemService.avancarStatus(1L)).thenReturn(ordemServico);

        mockMvc.perform(post("/ordens/1/avancar"))
                .andExpect(status().isOk());

        verify(ordemService, times(1)).avancarStatus(1L);
    }

    @Test
    @DisplayName("Deve retornar o status de uma ordem")
    void retornarStatus() throws Exception {
        OrdemServico ordemServico = new OrdemServico();

        when(ordemService.retornarStatus(1L)).thenReturn(ordemServico);

        mockMvc.perform(post("/ordens/1/retornar"))
                .andExpect(status().isOk());

        verify(ordemService, times(1)).retornarStatus(1L);
    }

    @Test
    @DisplayName("Deve retornar o tempo médio das ordens")
    void tempoMedio() throws Exception {
        when(ordemService.retornaTempoMedioDeOrdemServico()).thenReturn("2 horas");

        mockMvc.perform(get("/ordens/tempo-medio"))
                .andExpect(status().isOk())
                .andExpect(content().string("2 horas"));

        verify(ordemService, times(1)).retornaTempoMedioDeOrdemServico();
    }


    @Test
    @DisplayName("Deve retornar mensagem padrão quando não há ordens finalizadas")
    void tempoMedioSemOrdensFinalizadas() throws Exception {
        when(ordemService.retornaTempoMedioDeOrdemServico())
                .thenReturn("Nenhuma ordem de serviço finalizada foi encontrada.");

        mockMvc.perform(get("/ordens/tempo-medio"))
                .andExpect(status().isOk())
                .andExpect(content().string("Nenhuma ordem de serviço finalizada foi encontrada."));
    }

    @Test
    @DisplayName("Deve cancelar uma ordem com sucesso")
    void cancelarOrdemComSucesso() throws Exception {
        OrdemServico ordemServico = new OrdemServico();
        when(ordemService.cancelarOrdem(1L)).thenReturn(ordemServico);
        mockMvc.perform(post("/ordens/1/cancelar"))
                .andExpect(status().isOk());
        verify(ordemService, times(1)).cancelarOrdem(1L);
    }

}