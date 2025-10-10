package br.com.jlucaslopes.controller;

import br.com.jlucaslopes.auth.TokenService;
import br.com.jlucaslopes.model.request.AuthRequest;
import br.com.jlucaslopes.model.response.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserDetailsService userDetailsService;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        // instantiate a real TokenService to avoid inline-mock instrumentation issues on newer JDKs
        this.loginController = new LoginController(authManager, new TokenService(), userDetailsService);
    }

    @Test
    void loginReturnsTokenWhenCredentialsAreValid() {
        AuthRequest req = new AuthRequest();
        req.setUsername("alice");
        req.setPassword("pwd");

    // prepare user and authentication
    UserDetails user = User.withUsername("alice").password("pwd").roles("USER").build();
    Mockito.when(authManager.authenticate(Mockito.any())).thenReturn(
        new UsernamePasswordAuthenticationToken(user.getUsername(), req.getPassword(), user.getAuthorities())
    );
    Mockito.when(userDetailsService.loadUserByUsername("alice")).thenReturn(user);

        // real TokenService will generate a token; just assert it's present
        ResponseEntity<AuthResponse> response = loginController.login(req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
        assertFalse(response.getBody().getToken().isEmpty());
    }

    @Test
    void loginThrowsWhenAuthenticationFails() {
        AuthRequest req = new AuthRequest();
        req.setUsername("bob");
        req.setPassword("wrong");

        Mockito.when(authManager.authenticate(Mockito.any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> loginController.login(req));
    }

    @Test
    void loginThrowsWhenUserNotFound() {
        AuthRequest req = new AuthRequest();
        req.setUsername("carol");
        req.setPassword("pwd");

    Mockito.when(authManager.authenticate(Mockito.any())).thenReturn(
        new UsernamePasswordAuthenticationToken("carol", req.getPassword(), java.util.Collections.emptyList())
    );
        Mockito.when(userDetailsService.loadUserByUsername("carol"))
                .thenThrow(new UsernameNotFoundException("not found"));

        assertThrows(UsernameNotFoundException.class, () -> loginController.login(req));
    }
}
