package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.application.usecases.ordemservico.ListarOrdensUseCase;
import br.com.jlucaslopes.application.usecases.ordemservico.RetornaTempoMedioUseCase;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrdemControllerMvcTest {

    private MockMvc mockMvc;

    @Mock
    private OrdemServicoGateway gateway;

    @BeforeEach
    void setup() {
        var listar = new ListarOrdensUseCase(gateway);
        var tempo = new RetornaTempoMedioUseCase(gateway);

        var controller = new OrdemController(null, null, null, null, null, null, tempo, null, null, listar);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new br.com.jlucaslopes.infrastructure.controller.exceptionhandler.ControllerExceptionHandler())
                .build();
    }

    @Test
    void listarOrdens_returnsList() throws Exception {
        OrdemServico o1 = new OrdemServico(); o1.setId(1L);
        OrdemServico o2 = new OrdemServico(); o2.setId(2L);

        when(gateway.listarOrdemServicos()).thenReturn(List.of(o1, o2));

        mockMvc.perform(get("/ordens/ordens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void tempoMedio_returnsString() throws Exception {
        when(gateway.retornaTempoMedioDeOrdemServico()).thenReturn("2 dias");

        mockMvc.perform(get("/ordens/tempo-medio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("2 dias"));
    }
}
