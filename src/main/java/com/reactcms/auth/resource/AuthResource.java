package com.reactcms.auth.resource;

import com.reactcms.auth.dto.LoginRequest;
import com.reactcms.auth.dto.LoginResponse;
import com.reactcms.auth.dto.MeResponse;
import com.reactcms.auth.service.AuthService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;
    private final JsonWebToken jwt;

    public AuthResource(AuthService authService, JsonWebToken jwt) {
        this.authService = authService;
        this.jwt = jwt;
    }

    @POST
    @Path("/login")
    public LoginResponse login(@Valid LoginRequest request) {
        return authService.login(request);
    }

    @POST
    @Path("/logout")
    public Response logout() {
        return Response.noContent().build();
    }

    @GET
    @Path("/me")
    public MeResponse me() {
        return authService.me(jwt.getSubject());
    }
}
