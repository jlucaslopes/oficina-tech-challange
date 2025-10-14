package br.com.jlucaslopes.infrastructure.gateways.token;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final String FIAP_USER = "fiap";

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (FIAP_USER.equals(username)) {
            String encodedPassword = new BCryptPasswordEncoder().encode("1234");
            return User.withUsername(FIAP_USER)
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
