package br.com.jlucaslopes.config;

import br.com.jlucaslopes.application.gateways.TokenGateway;
import br.com.jlucaslopes.application.usecases.token.ExtractUsernameUseCase;
import br.com.jlucaslopes.application.usecases.token.GenerateTokenUseCase;
import br.com.jlucaslopes.application.usecases.token.ValidateTokenUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    @Bean
    public ExtractUsernameUseCase extractUsernameUseCase(TokenGateway tokenGateway) {
        return new ExtractUsernameUseCase(tokenGateway);
    }

    @Bean
    public GenerateTokenUseCase generateTokenUseCase(TokenGateway tokenGateway) {
        return new GenerateTokenUseCase(tokenGateway);
    }

    @Bean
    public ValidateTokenUseCase validateTokenUseCase(TokenGateway tokenGateway) {
        return new ValidateTokenUseCase(tokenGateway);
    }

}
