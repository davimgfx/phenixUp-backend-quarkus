package org.acme.security.jwt;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Client;
import org.eclipse.microprofile.config.inject.ConfigProperty;


import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
public class GenerateToken {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateTokenJWT(Client client) {
        String token =
                Jwt.issuer(issuer)
                        .upn(client.getEmail())
                        .groups(new HashSet<>(Arrays.asList("User")))
                        .sign();
        System.out.println(token);

        return token;
    }
}
