package br.com.jlucaslopes.config;

import br.com.jlucaslopes.application.gateways.ClienteGateway;
import br.com.jlucaslopes.application.usecases.cliente.DeleteClienteByIdUseCase;
import br.com.jlucaslopes.application.usecases.cliente.FindClienteByDocumentoUseCase;
import br.com.jlucaslopes.application.usecases.cliente.FindClienteByIdUseCase;
import br.com.jlucaslopes.application.usecases.cliente.SaveClienteUseCase;
import br.com.jlucaslopes.infrastructure.gateways.cliente.ClienteGatewayImpl;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteConfig {

    @Bean
    public DeleteClienteByIdUseCase deleteClienteByIdUseCase(ClienteGateway clienteGateway) {
        return new DeleteClienteByIdUseCase(clienteGateway);
    }

    @Bean
    public FindClienteByIdUseCase findClienteByIdUseCase(ClienteGateway clienteGateway) {
        return new FindClienteByIdUseCase(clienteGateway);
    }

    @Bean
    public FindClienteByDocumentoUseCase findClienteByDocumentoUseCase(ClienteGateway clienteGateway) {
        return new FindClienteByDocumentoUseCase(clienteGateway);
    }

    @Bean
    public SaveClienteUseCase saveClienteUseCase(ClienteGateway clienteGateway) {
        return new SaveClienteUseCase(clienteGateway);
    }

    @Bean
    public ClienteGateway clienteGateway(ClienteRepository repository) {
        return new ClienteGatewayImpl(repository);
    }
}
