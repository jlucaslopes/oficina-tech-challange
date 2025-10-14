package br.com.jlucaslopes.infrastructure.gateways.cliente;

import br.com.jlucaslopes.domain.entities.Cliente;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteEntity;

public class ClienteMapper {

    public static ClienteEntity toEntity(Cliente cliente) {
        if(cliente.getId() == null) {
            return new ClienteEntity(
                    cliente.getNome(),
                    cliente.getDocumento()
            );
        }
        return new ClienteEntity(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDocumento()
        );
    }

    public static Cliente toCliente(ClienteEntity clienteEntity) {
        return new Cliente(
                clienteEntity.getId(),
                clienteEntity.getNome(),
                clienteEntity.getDocumento()
        );
    }
}
