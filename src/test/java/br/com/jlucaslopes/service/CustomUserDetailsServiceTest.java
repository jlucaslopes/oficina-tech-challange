package br.com.jlucaslopes.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {

    @Test
    void loadUserByUsername_whenFiap_returnsUserDetails() {
        CustomUserDetailsService service = new CustomUserDetailsService();

        UserDetails user = service.loadUserByUsername("fiap");

        assertNotNull(user);
        assertEquals("fiap", user.getUsername());

        // password must be encoded and not empty
        assertNotNull(user.getPassword());
        assertFalse(user.getPassword().isBlank());

        // should contain ROLE_USER authority
        Set<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertTrue(authorities.contains("ROLE_USER"));
    }

    @Test
    void loadUserByUsername_whenDifferent_throwsUsernameNotFoundException() {
        CustomUserDetailsService service = new CustomUserDetailsService();

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("other"));

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    @Test
    void loadUserByUsername_whenNull_throwsUsernameNotFoundException() {
        CustomUserDetailsService service = new CustomUserDetailsService();

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername(null));
    }

    @Test
    void passwords_areDifferent_betweenCalls() {
        CustomUserDetailsService service = new CustomUserDetailsService();

        UserDetails first = service.loadUserByUsername("fiap");
        UserDetails second = service.loadUserByUsername("fiap");

        // implementation uses a random UUID and BCrypt; encoded passwords should differ
        assertNotNull(first.getPassword());
        assertNotNull(second.getPassword());
        assertNotEquals(first.getPassword(), second.getPassword());
    }
}
