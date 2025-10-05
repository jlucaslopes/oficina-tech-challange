package br.com.jlucaslopes.infrastructure.persistence.ordemservico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long> {

    List<OrdemServicoEntity> findAllByStatus(Status status);

    @Query("SELECT o FROM OrdemServicoEntity o WHERE o.status NOT IN (:excluidos) ORDER BY " +
            "CASE o.status " +
            "  WHEN 'EM_EXECUCAO' THEN 1 " +
            "  WHEN 'AGUARDANDO_APROVACAO' THEN 2 " +
            "  WHEN 'EM_DIAGNOSTICO' THEN 3 " +
            "  WHEN 'RECEBIDA' THEN 4 " +
            "END, o.dataInicio ASC")
    List<OrdemServicoEntity> findAllForListagem(@Param("excluidos") List<Status> excluidos);

}

