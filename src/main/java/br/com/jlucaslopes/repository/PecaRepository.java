package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<Peca, Long> {
}
