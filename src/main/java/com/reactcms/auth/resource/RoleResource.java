package com.reactcms.auth.resource;

import com.reactcms.auth.dto.RoleDto;
import com.reactcms.auth.service.RolePermissionService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/roles")
@Produces(MediaType.APPLICATION_JSON)
public class RoleResource {

    private final RolePermissionService rolePermissionService;

    public RoleResource(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GET
    public List<RoleDto> list() {
        return rolePermissionService.listRoles();
    }

    @GET
    @Path("/{id}")
    public RoleDto get(@PathParam("id") Integer id) {
        return rolePermissionService.getRole(id);
    }
}
