package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.OrdemServico;
import br.com.jlucaslopes.model.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdemServicoRepositoryTest {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Test
    @DisplayName("findAllByStatus retorna ordens quando existem com o status")
    void findAllByStatusRetornaOrdensQuandoExistem() {
        OrdemServico ordem1 = new OrdemServico();
        ordem1.setStatus(Status.AGUARDANDO_APROVACAO);
        ordemServicoRepository.save(ordem1);

        OrdemServico ordem2 = new OrdemServico();
        ordem2.setStatus(Status.AGUARDANDO_APROVACAO);
        ordemServicoRepository.save(ordem2);

        OrdemServico ordem3 = new OrdemServico();
        ordem3.setStatus(Status.ENTREGUE);
        ordemServicoRepository.save(ordem3);

        List<OrdemServico> aguardando = ordemServicoRepository.findAllByStatus(Status.AGUARDANDO_APROVACAO);
        List<OrdemServico> entregue = ordemServicoRepository.findAllByStatus(Status.ENTREGUE);

        assertEquals(2, aguardando.size());
        assertEquals(1, entregue.size());
    }

    @Test
    @DisplayName("findAllByStatus retorna lista vazia quando não há ordens com o status")
    void findAllByStatusRetornaListaVaziaQuandoNaoHaOrdens() {
        List<OrdemServico> result = ordemServicoRepository.findAllByStatus(Status.FINALIZADA);
        assertTrue(result.isEmpty());
    }
}