package br.com.jlucaslopes.application.usecases.token;

import br.com.jlucaslopes.application.gateways.TokenGateway;
import org.springframework.security.core.userdetails.UserDetails;

public class GenerateTokenUseCase {

    private final TokenGateway tokenGateway;

    public GenerateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public String generateToken(UserDetails userDetails) {
       return this.tokenGateway.generateToken(userDetails);
    }
}
