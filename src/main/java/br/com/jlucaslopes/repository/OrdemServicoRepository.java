package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.OrdemServico;
import br.com.jlucaslopes.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    List<OrdemServico> findAllByStatus(Status status);
}
