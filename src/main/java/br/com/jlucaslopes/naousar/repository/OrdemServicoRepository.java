package br.com.jlucaslopes.naousar.repository;

import br.com.jlucaslopes.naousar.model.OrdemServico;
import br.com.jlucaslopes.naousar.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    List<OrdemServico> findAllByStatus(Status status);
}
