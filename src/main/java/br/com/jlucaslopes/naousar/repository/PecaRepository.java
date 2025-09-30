package br.com.jlucaslopes.naousar.repository;

import br.com.jlucaslopes.naousar.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PecaRepository extends JpaRepository<Peca, Long> {
    Optional<Peca> findPecaByDescricao(String descricao);
}
