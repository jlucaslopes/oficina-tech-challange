package br.com.jlucaslopes.infrastructure.controller;


import br.com.jlucaslopes.application.gateways.TokenGateway;
import br.com.jlucaslopes.domain.request.AuthRequest;
import br.com.jlucaslopes.domain.response.AuthResponse;
import br.com.jlucaslopes.infrastructure.gateways.token.TokenServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authManager;
    private final TokenGateway tokenGateway;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authManager,
                           TokenGateway tokenGateway,
                           UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.tokenGateway = tokenGateway;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = tokenGateway.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
