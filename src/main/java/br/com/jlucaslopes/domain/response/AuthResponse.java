package br.com.jlucaslopes.domain.response;

public class AuthResponse {
    private String token;

    public AuthResponse(String jwt) {
        this.token = jwt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
