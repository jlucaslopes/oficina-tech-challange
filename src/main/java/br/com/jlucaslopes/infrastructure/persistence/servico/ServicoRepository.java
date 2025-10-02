package br.com.jlucaslopes.infrastructure.persistence.servico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<ServicoEntity, Long> {
}
