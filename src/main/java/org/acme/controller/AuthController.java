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
import org.acme.DTO.ClientResponseDTO;
import org.acme.entity.Client;
import org.acme.repository.ClientRepositoryImpl;
import org.acme.security.jwt.GenerateToken;

import java.time.LocalDateTime;
import java.util.Random;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    ClientRepositoryImpl clientRepository;

    @Inject
    GenerateToken token;

    private final Random random = new Random();


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
        ClientResponseDTO clientDTO = new ClientResponseDTO(jwtToken);

        user.persist();


        return Response.ok(clientDTO).build();
    }

    @POST
    @Path("/login/token")
    @Transactional
    public Response tokenLoginEmail(ClientDTO userDTO) {
        Client user = clientRepository.findByEmail(userDTO.email);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid email")
                    .build();
        }

        int randomToken = 100000 + this.random.nextInt(900000); // Gerar número no formato XXXXXX


        user.setToken(String.valueOf(randomToken));
        user.setValidateToken(LocalDateTime.now().plusDays(1)); // Define validade para um dia depois
        user.persist();

        return Response.ok(randomToken).build();
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

        ClientResponseDTO clientDTO = new ClientResponseDTO(jwtToken);

        return Response.ok(clientDTO).build();
    }
}