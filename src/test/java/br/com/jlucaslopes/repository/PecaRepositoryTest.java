package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.Peca;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PecaRepositoryTest {

    @Autowired
    private PecaRepository pecaRepository;

    @Test
    @DisplayName("findPecaByDescricao retorna peça quando existe")
    void findPecaByDescricaoRetornaPecaQuandoExiste() {
        Peca peca = new Peca();
        peca.setDescricao("Filtro de óleo");
        pecaRepository.save(peca);

        Optional<Peca> result = pecaRepository.findPecaByDescricao("Filtro de óleo");

        assertTrue(result.isPresent());
        assertEquals("Filtro de óleo", result.get().getDescricao());
    }

    @Test
    @DisplayName("findPecaByDescricao retorna vazio quando não existe")
    void findPecaByDescricaoRetornaVazioQuandoNaoExiste() {
        Optional<Peca> result = pecaRepository.findPecaByDescricao("Inexistente");
        assertFalse(result.isPresent());
    }
}