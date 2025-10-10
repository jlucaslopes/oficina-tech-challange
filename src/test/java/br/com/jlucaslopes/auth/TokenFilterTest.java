package br.com.jlucaslopes.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenFilterTest {

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_noAuthorizationHeader_callsChain() throws ServletException, IOException {
        TokenService jwtService = new TokenService() {};
        UserDetailsService uds = mock(UserDetailsService.class);
        TokenFilter filter = new TokenFilter(jwtService, uds);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(req, res, chain);

        verify(chain).doFilter(req, res);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_invalidPrefix_callsChain() throws ServletException, IOException {
        TokenService jwtService = new TokenService() {};
        UserDetailsService uds = mock(UserDetailsService.class);
        TokenFilter filter = new TokenFilter(jwtService, uds);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getHeader("Authorization")).thenReturn("Token abc");

        filter.doFilterInternal(req, res, chain);

        verify(chain).doFilter(req, res);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_validToken_setsAuthentication() throws ServletException, IOException {
        TokenService jwtService = new TokenService() {
            @Override
            public String extractUsername(String token) {
                return "alice";
            }

            @Override
            public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
                return true;
            }
        };
        UserDetailsService uds = mock(UserDetailsService.class);
        TokenFilter filter = new TokenFilter(jwtService, uds);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

    when(req.getHeader("Authorization")).thenReturn("Bearer valid.token");

        UserDetails user = User.withUsername("alice").password("pwd").roles("USER").build();
    when(uds.loadUserByUsername("alice")).thenReturn(user);

        filter.doFilterInternal(req, res, chain);

        verify(chain).doFilter(req, res);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("alice", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void doFilterInternal_invalidToken_doesNotSetAuthentication() throws ServletException, IOException {
        TokenService jwtService = new TokenService() {
            @Override
            public String extractUsername(String token) {
                return "bob";
            }

            @Override
            public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
                return false;
            }
        };
        UserDetailsService uds = mock(UserDetailsService.class);
        TokenFilter filter = new TokenFilter(jwtService, uds);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

    when(req.getHeader("Authorization")).thenReturn("Bearer invalid.token");

        UserDetails user = User.withUsername("bob").password("pwd").roles("USER").build();
    when(uds.loadUserByUsername("bob")).thenReturn(user);

        filter.doFilterInternal(req, res, chain);

        verify(chain).doFilter(req, res);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
