package org.acme.security.jwt;

import io.smallrye.jwt.build.Jwt;

import java.time.Duration;
import java.util.Set;

public class JWTService {
    public static String generateToken(String email){
        return Jwt.issuer("your-issuer")
                .upn(email)
                .groups(Set.of("Client"))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}
