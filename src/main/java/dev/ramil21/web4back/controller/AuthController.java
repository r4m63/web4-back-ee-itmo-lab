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

import java.rmi.ServerException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


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
            return Response.ok(tokens).build();
        } catch (UserEmailExistsException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ServerException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogin() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", "sample_jwt_token");
        return Response.ok(response).build();
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return Response.ok(response).build();
    }

    @GET
    @Path("/activate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doActivate(@QueryParam("link") String activationLink) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Activation successful");
        response.put("link", activationLink);
        return Response.ok(response).build();
    }

    @GET
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doRefresh() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Token refreshed successfully");
        response.put("newToken", "new_sample_jwt_token");
        return Response.ok(response).build();
    }

    @GET
    @Path("/test-auth-user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testAuthUser() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Authenticated user test successful");
        return Response.ok(response).build();
    }

}
