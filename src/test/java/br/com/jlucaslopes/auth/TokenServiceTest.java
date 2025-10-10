package br.com.jlucaslopes.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    @Test
    void generate_and_extract_username() {
        TokenService service = new TokenService();

        UserDetails user = User.withUsername("alice").password("pwd").roles("USER").build();
        String token = service.generateToken(user);

        assertNotNull(token);

        String extracted = service.extractUsername(token);
        assertEquals("alice", extracted);
    }

    @Test
    void isTokenValid_returnsTrueForValidToken() {
        TokenService service = new TokenService();
        UserDetails user = User.withUsername("bob").password("pwd").roles("USER").build();

        String token = service.generateToken(user);

        assertTrue(service.isTokenValid(token, user));
    }

    @Test
    void isTokenValid_returnsFalseForDifferentUser() {
        TokenService service = new TokenService();
        UserDetails alice = User.withUsername("alice").password("pwd").roles("USER").build();
        UserDetails bob = User.withUsername("bob").password("pwd").roles("USER").build();

        String token = service.generateToken(alice);

        assertFalse(service.isTokenValid(token, bob));
    }

    @Test
    void isTokenExpired_detectsExpiredToken() throws Exception {
        // Create a TokenService variant that can generate an already-expired token
        class ExpiringTokenService extends TokenService {
            public String generateExpiredToken(UserDetails userDetails) {
                return io.jsonwebtoken.Jwts.builder()
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new java.util.Date(System.currentTimeMillis() - 1000 * 60 * 60))
                        .setExpiration(new java.util.Date(System.currentTimeMillis() - 1000))
                        .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(this.SECRET_KEY_BYTES()), io.jsonwebtoken.SignatureAlgorithm.HS256)
                        .compact();
            }

            // helper to expose the secret key bytes used internally
            private byte[] SECRET_KEY_BYTES() {
                try {
                    java.lang.reflect.Field f = TokenService.class.getDeclaredField("SECRET_KEY");
                    f.setAccessible(true);
                    String sk = (String) f.get(this);
                    return sk.getBytes();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        ExpiringTokenService service = new ExpiringTokenService();
        UserDetails user = User.withUsername("charlie").password("pwd").roles("USER").build();

    String expiredToken = service.generateExpiredToken(user);

    org.junit.jupiter.api.Assertions.assertThrows(io.jsonwebtoken.ExpiredJwtException.class,
        () -> service.isTokenValid(expiredToken, user));
    }
}
