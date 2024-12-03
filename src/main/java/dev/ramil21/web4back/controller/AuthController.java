package dev.ramil21.web4back.controller;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
     *
     * /test-auth-user      GET
     */


    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doRegistration() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return Response.ok(response).build();
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
