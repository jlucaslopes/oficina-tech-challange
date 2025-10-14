package br.com.jlucaslopes.infrastructure.persistence.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity,Long> {

    List<VeiculoEntity> findByClienteDocumento(String documento);

    Optional<VeiculoEntity> findByPlaca(String placa);
}