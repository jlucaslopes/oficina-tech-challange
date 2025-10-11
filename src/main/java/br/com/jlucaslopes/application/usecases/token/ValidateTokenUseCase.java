package br.com.jlucaslopes.application.usecases.token;

import br.com.jlucaslopes.application.gateways.TokenGateway;
import org.springframework.security.core.userdetails.UserDetails;

public class ValidateTokenUseCase {

    private final TokenGateway tokenGateway;

    public ValidateTokenUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public boolean isTokenValid(String token, UserDetails username) {
       return this.tokenGateway.isTokenValid(token, username);
    }
}
