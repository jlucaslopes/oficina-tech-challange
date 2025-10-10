package br.com.jlucaslopes.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UserDetailsService;


class SecurityConfigTest {

    @Test
    void passwordEncoder_returnsBCrypt() {
        // create a simple TokenFilter instance without using Mockito to avoid inline mock instrumentation
        UserDetailsService uds = username -> null;
        TokenFilter tf = new TokenFilter(new TokenService(), uds);
        SecurityConfig cfg = new SecurityConfig(tf);

        PasswordEncoder encoder = cfg.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void authenticationManager_delegatesToConfiguration() throws Exception {
    UserDetailsService uds = username -> null;
    TokenFilter tf = new TokenFilter(new TokenService(), uds);
    SecurityConfig cfg = new SecurityConfig(tf);

    AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
    AuthenticationManager manager = mock(AuthenticationManager.class);
    when(config.getAuthenticationManager()).thenReturn(manager);

        AuthenticationManager result = cfg.authenticationManager(config);

        assertSame(manager, result);
    }

    @Test
    void filterChain_buildsUsingPassedHttpSecurity() throws Exception {
    UserDetailsService uds2 = username -> null;
    TokenFilter tokenFilter = new TokenFilter(new TokenService(), uds2);
    SecurityConfig cfg = new SecurityConfig(tokenFilter);

    // Use deep-stubbed HttpSecurity to avoid dealing with complex generics in configurers
    org.springframework.security.config.annotation.web.builders.HttpSecurity http =
        mock(org.springframework.security.config.annotation.web.builders.HttpSecurity.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);

    when(http.addFilterBefore(eq(tokenFilter), eq(UsernamePasswordAuthenticationFilter.class))).thenReturn(http);

    org.springframework.security.web.DefaultSecurityFilterChain chain =
        mock(org.springframework.security.web.DefaultSecurityFilterChain.class);
    when(http.build()).thenReturn(chain);

    SecurityFilterChain result = cfg.filterChain(http);

    assertSame(chain, result);
    }


}
