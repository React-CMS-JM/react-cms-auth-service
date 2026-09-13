package com.reactcms.auth.resource;

import com.reactcms.auth.dto.PermissionDto;
import com.reactcms.auth.service.RolePermissionService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/permissions")
@Produces(MediaType.APPLICATION_JSON)
public class PermissionResource {

    private final RolePermissionService rolePermissionService;

    public PermissionResource(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GET
    public List<PermissionDto> list() {
        return rolePermissionService.listPermissions();
    }
}
