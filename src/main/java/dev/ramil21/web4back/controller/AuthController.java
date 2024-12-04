package dev.ramil21.web4back.controller;

import dev.ramil21.web4back.dto.TokensDto;
import dev.ramil21.web4back.dto.UserDto;
import dev.ramil21.web4back.exceptions.UserEmailExistsException;
import dev.ramil21.web4back.service.AuthService;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.rmi.ServerException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Path("/auth")
public class AuthController {

    /**
     * /registration        POST
     * /login               POST
     * /logout              POST
     * /activate/:link      GET
     * /refresh             GET
     * <p>
     * /test-auth-user      GET
     */

    @Inject
    private AuthService authService;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doRegistration(@Valid UserDto userDto) {
        try {
            TokensDto tokens = authService.registerUser(userDto.getEmail(), userDto.getPassword());
            log.info("Successfully registered user: {}", userDto.getEmail());
            return Response.ok(tokens).build();
        } catch (UserEmailExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ServerException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
