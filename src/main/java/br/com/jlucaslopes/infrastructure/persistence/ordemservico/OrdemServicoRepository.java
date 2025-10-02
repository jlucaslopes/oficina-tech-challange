package br.com.jlucaslopes.infrastructure.persistence.ordemservico;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long> {

    List<OrdemServicoEntity> findAllByStatus(Status status);
}
