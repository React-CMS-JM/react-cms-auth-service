package com.reactcms.auth.service;

import com.reactcms.auth.dto.PermissionDto;
import com.reactcms.auth.dto.RoleDto;
import com.reactcms.auth.entity.PermissionEntity;
import com.reactcms.auth.entity.RoleEntity;
import com.reactcms.auth.util.DtoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class RolePermissionService {

    public List<RoleDto> listRoles() {
        return RoleEntity.<RoleEntity>listAll().stream()
                .sorted(Comparator.comparing(r -> r.id))
                .map(DtoMapper::toRoleDto)
                .toList();
    }

    public RoleDto getRole(Integer id) {
        RoleEntity role = RoleEntity.findById(id);
        if (role == null) {
            throw new NotFoundException("Role not found: " + id);
        }
        return DtoMapper.toRoleDto(role);
    }

    public List<PermissionDto> listPermissions() {
        return PermissionEntity.<PermissionEntity>listAll().stream()
                .sorted(Comparator.comparing(p -> p.id))
                .map(DtoMapper::toPermissionDto)
                .toList();
    }
}
