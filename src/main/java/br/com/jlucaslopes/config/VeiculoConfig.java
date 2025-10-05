package br.com.jlucaslopes.config;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.application.usecases.veiculo.*;
import br.com.jlucaslopes.infrastructure.gateways.veiculo.VeiculoGatewayImpl;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VeiculoConfig {

    @Bean
    public DeleteVeiculoByIdUseCase deleteVeiculoByIdUseCase(VeiculoGateway gateway){
        return new DeleteVeiculoByIdUseCase(gateway);
    }

    @Bean
    public FindVeiculoByPlacaUseCase findVeiculoByPlacaUseCase(VeiculoGateway gateway){
        return new FindVeiculoByPlacaUseCase(gateway);
    }

    @Bean
    public FindVeiculoPorIdUseCase findVeiculoPorIdUseCase(VeiculoGateway gateway){
        return new FindVeiculoPorIdUseCase(gateway);
    }

    @Bean
    public FindVeiculosByClienteDocumentoUseCase findVeiculosByClienteDocumentoUseCase(VeiculoGateway gateway){
        return new FindVeiculosByClienteDocumentoUseCase(gateway);
    }

    @Bean
    public SalvarVeiculoUseCase salvarVeiculoUseCase(VeiculoGateway gateway){
        return new SalvarVeiculoUseCase(gateway);
    }

    @Bean
    public UpdateVeiculoByIdUseCase updateVeiculoByIdUseCase(VeiculoGateway gateway){
        return new UpdateVeiculoByIdUseCase(gateway);
    }

    @Bean
    public VeiculoGateway veiculoGateway( ClienteRepository clienteRepository, VeiculoRepository veiculoRepository){
        return new VeiculoGatewayImpl(clienteRepository, veiculoRepository);
    }

}
