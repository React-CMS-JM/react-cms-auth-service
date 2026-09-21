package com.reactcms.auth.resource;

import com.reactcms.auth.dto.BanUserRequest;
import com.reactcms.auth.dto.CreateUserRequest;
import com.reactcms.auth.dto.UpdateUserRequest;
import com.reactcms.auth.dto.UserDto;
import com.reactcms.auth.dto.UserStatsDto;
import com.reactcms.auth.dto.UserSummaryDto;
import com.reactcms.auth.service.AuthService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final AuthService authService;

    public UserResource(AuthService authService) {
        this.authService = authService;
    }

    @GET
    public List<UserDto> list() {
        return authService.listUsers();
    }

    @GET
    @Path("/stats")
    public UserStatsDto stats() {
        return authService.userStats();
    }

    @GET
    @Path("/by-ids")
    public List<UserSummaryDto> byIds(@QueryParam("ids") String ids) {
        if (ids == null || ids.isBlank()) {
            return List.of();
        }
        List<String> parsed = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        return authService.usersByIds(parsed);
    }

    @GET
    @Path("/{id}")
    public UserDto get(@PathParam("id") String id) {
        return authService.getUser(id);
    }

    @POST
    public Response create(@Valid CreateUserRequest request) {
        UserDto created = authService.createUser(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public UserDto update(@PathParam("id") String id, @Valid UpdateUserRequest request) {
        return authService.updateUser(id, request);
    }

    @POST
    @Path("/{id}/ban")
    public UserDto ban(@PathParam("id") String id, @Valid BanUserRequest request) {
        return authService.banUser(id, request.reason);
    }

    @POST
    @Path("/{id}/unban")
    public UserDto unban(@PathParam("id") String id) {
        return authService.unbanUser(id);
    }
}
