package br.com.jlucaslopes.domain.request;

import br.com.jlucaslopes.domain.entities.Peca;
import br.com.jlucaslopes.domain.entities.Servico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoCreateRequestTest {

    @Test
    void toServicoWithPecaCalculatesPrice() {
        ServicoCreateRequest req = new ServicoCreateRequest();
        req.setNome("Troca filtro");
        req.setQuantidade(2L);
        req.setIdPeca(1L);

        Peca peca = new Peca(1L, "Filtro", 30.0, 10);

        Servico servico = req.toServico(peca);

        assertNotNull(servico);
        assertEquals("Troca filtro", servico.getNome());
        assertEquals(2L, servico.getQuantidade());
        assertEquals(60.0, servico.getPreco());
        assertNotNull(servico.getPeca());
    }

    @Test
    void toServicoWithoutPecaUsesPrecoField() {
        ServicoCreateRequest req = new ServicoCreateRequest();
        req.setNome("Mão de obra");
        req.setQuantidade(1L);
        req.setPreco(150.0);

        Servico servico = req.toServico();

        assertNotNull(servico);
        assertEquals(150.0, servico.getPreco());
        assertEquals("Mão de obra", servico.getNome());
    }
}
