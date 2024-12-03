package org.acme.controller;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.ClientDTO;
import org.acme.entity.Client;
import org.acme.repository.ClientRepositoryImpl;
import org.acme.security.jwt.GenerateToken;
import org.acme.security.jwt.JWTService;

import java.time.LocalDateTime;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    ClientRepositoryImpl clientRepository;

    @Inject
    GenerateToken token;


    @POST
    @Path("/signup")
    @Transactional
    public Response signUp(ClientDTO userDTO) {
        if (clientRepository.findByEmail(userDTO.email) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email already in use")
                    .build();
        }

        Client user = new Client();
        user.email = userDTO.email;
        user.name = userDTO.name;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();

        // Gerar o token
        String jwtToken = token.generateTokenJWT(user);
        System.out.println(jwtToken);

        user.persist();


        return Response.ok(jwtToken).build();
    }

    @POST
    @Path("/login")
    @Transactional
    public Response login(ClientDTO userDTO) {
        Client user = clientRepository.findByEmail(userDTO.email);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid email")
                    .build();
        }

        String jwtToken = token.generateTokenJWT(user);

        return Response.ok(jwtToken).build();
    }
}