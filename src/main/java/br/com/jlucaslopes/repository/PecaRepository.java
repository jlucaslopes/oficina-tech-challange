package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca, Long> {
    Optional<Peca> findPecaByDescricao(String descricao);
}
