package br.com.jlucaslopes.infrastructure.controller;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.application.usecases.cliente.*;
import br.com.jlucaslopes.domain.entities.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerMvcTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClienteGateway clienteGateway;

    @BeforeEach
    void setup() {
        var deleteUseCase = new DeleteClienteByIdUseCase(clienteGateway);
        var findByDoc = new FindClienteByDocumentoUseCase(clienteGateway);
        var findById = new FindClienteByIdUseCase(clienteGateway);
        var save = new SaveClienteUseCase(clienteGateway);

        var controller = new ClienteController(deleteUseCase, findByDoc, findById, save);

    this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new br.com.jlucaslopes.infrastructure.controller.exceptionhandler.ControllerExceptionHandler())
        .build();
    }

    @Test
    void getClienteById_returnsOkAndJson() throws Exception {
        Cliente cliente = new Cliente(1L, "Nome", "12345678901");
        when(clienteGateway.findClienteById(1)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Nome"))
                .andExpect(jsonPath("$.documento").value("12345678901"));
    }

    @Test
    void getClienteByDocumento_returnsOk() throws Exception {
        Cliente cliente = new Cliente(2L, "Outro", "98765432100");
        when(clienteGateway.findClienteByDocumento("98765432100")).thenReturn(cliente);

        mockMvc.perform(get("/cliente/documento/98765432100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void createCliente_returnsSavedCliente() throws Exception {
        Cliente input = new Cliente("11122233344", "Novo Cliente");
        Cliente saved = new Cliente(4L, "Novo Cliente", "11122233344");

        when(clienteGateway.save(any(Cliente.class))).thenReturn(saved);

        mockMvc.perform(post("/cliente/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.nome").value("Novo Cliente"));
    }

    @Test
    void getClienteByDocumento_whenGatewayThrowsIllegalArgument_returnsBadRequest() throws Exception {
        when(clienteGateway.findClienteByDocumento("bad")).thenThrow(new IllegalArgumentException("doc invalido"));

        mockMvc.perform(get("/cliente/documento/bad").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("doc invalido"));
    }
}
