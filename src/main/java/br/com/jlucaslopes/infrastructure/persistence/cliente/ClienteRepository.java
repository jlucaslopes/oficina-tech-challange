package br.com.jlucaslopes.infrastructure.persistence.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository  extends JpaRepository<ClienteEntity, Integer> {

     Optional<ClienteEntity> findClienteByDocumento(String documento);
}
