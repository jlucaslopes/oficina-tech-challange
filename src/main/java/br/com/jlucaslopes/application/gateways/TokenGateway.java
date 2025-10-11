package br.com.jlucaslopes.application.gateways;


import org.springframework.security.core.userdetails.UserDetails;


public interface TokenGateway {

     String generateToken(UserDetails userDetails);

     String extractUsername(String token);

     boolean isTokenValid(String token, UserDetails userDetails) ;

}
