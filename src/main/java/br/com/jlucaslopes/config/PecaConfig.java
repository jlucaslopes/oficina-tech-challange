package br.com.jlucaslopes.config;

import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.application.usecases.peca.*;
import br.com.jlucaslopes.infrastructure.gateways.peca.PecaServiceImpl;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PecaConfig {

   @Bean
   public AtualizarEstoquePecaUseCase atualizarEstoquePecaUseCase(PecaGateway pecaGateway){
       return new AtualizarEstoquePecaUseCase(pecaGateway);
   }

   @Bean
   public AtualizarPecaUseCase atualizarPecaUseCase(PecaGateway pecaGateway){
       return new AtualizarPecaUseCase(pecaGateway);
   }

   @Bean
   public BuscarPecaPorIdUseCase buscarPecaPorIdUseCase(PecaGateway pecaGateway){
       return new BuscarPecaPorIdUseCase(pecaGateway);
   }

   @Bean
   public DeletarPecaUseCase deletarPecaUseCase(PecaGateway pecaGateway){
       return new DeletarPecaUseCase(pecaGateway);
   }

   @Bean
   public SalvarPecaUseCase salvarPecaUseCase(PecaGateway pecaGateway){
       return new SalvarPecaUseCase(pecaGateway);
   }

   @Bean
    public PecaServiceImpl pecaGateway(PecaRepository repository){
       return new PecaServiceImpl(repository);
   }
}
