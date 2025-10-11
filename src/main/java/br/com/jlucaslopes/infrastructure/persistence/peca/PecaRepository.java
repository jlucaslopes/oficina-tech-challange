package br.com.jlucaslopes.infrastructure.persistence.peca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PecaRepository extends JpaRepository<PecaEntity, Long> {
    Optional<PecaEntity> findPecaByDescricao(String descricao);
}
