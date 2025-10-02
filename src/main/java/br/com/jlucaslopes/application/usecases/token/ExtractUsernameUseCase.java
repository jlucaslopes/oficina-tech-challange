package br.com.jlucaslopes.application.usecases.token;

import br.com.jlucaslopes.application.gateways.TokenGateway;

public class ExtractUsernameUseCase {

    private final TokenGateway tokenGateway;

    public ExtractUsernameUseCase(TokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public String extractUsername(String token) {
       return this.tokenGateway.extractUsername(token);
    }
}
