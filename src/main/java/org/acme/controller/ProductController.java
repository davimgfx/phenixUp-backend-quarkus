package org.acme.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.ClientDTO;
import org.acme.DTO.ClientResponseDTO;
import org.acme.entity.Client;

@Path("/products")
public class ProductController {

    @GET
    @RolesAllowed("User")
    @Transactional
    @Path("/user")
    public String productUser( ) {
        return "User OK";
    }

    @GET
    @RolesAllowed("Admin")
    @Transactional
    @Path("/admin")
    public String productAdmin( ) {
        return "Admin OK";
    }
}