package br.com.jlucaslopes.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final String FIAP_USER = "fiap";

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (FIAP_USER.equals(username)) {
            return User.withUsername(FIAP_USER)
                    .password(new BCryptPasswordEncoder().encode("1234"))
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
