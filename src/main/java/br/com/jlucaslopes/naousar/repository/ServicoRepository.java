package br.com.jlucaslopes.naousar.repository;

import br.com.jlucaslopes.naousar.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
