package br.com.jlucaslopes.repository;

import br.com.jlucaslopes.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

    List<Veiculo> findByClienteDocumento(String documento);
}
