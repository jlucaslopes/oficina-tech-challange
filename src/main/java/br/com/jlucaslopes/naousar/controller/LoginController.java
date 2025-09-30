package br.com.jlucaslopes.naousar.controller;


import br.com.jlucaslopes.naousar.auth.TokenService;
import br.com.jlucaslopes.domain.entities.request.AuthRequest;
import br.com.jlucaslopes.domain.entities.response.AuthResponse;
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
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authManager, TokenService tokenService, UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
