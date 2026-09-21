package com.reactcms.auth.util;

import com.reactcms.auth.dto.PermissionDto;
import com.reactcms.auth.dto.RoleDto;
import com.reactcms.auth.dto.UserDto;
import com.reactcms.auth.dto.UserSummaryDto;
import com.reactcms.auth.entity.PermissionEntity;
import com.reactcms.auth.entity.RoleEntity;
import com.reactcms.auth.entity.UserEntity;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class DtoMapper {

    private DtoMapper() {
    }

    public static UserDto toUserDto(UserEntity user) {
        List<Integer> roleIds = user.roles.stream()
                .map(r -> r.id)
                .sorted()
                .toList();
        return new UserDto(
                user.id,
                user.email,
                user.firstName,
                user.lastName,
                AvatarColor.fromEmail(user.email),
                Boolean.TRUE.equals(user.isBanned),
                user.banReason,
                roleIds,
                user.createdAt,
                user.updatedAt);
    }

    public static UserSummaryDto toUserSummaryDto(UserEntity user) {
        return new UserSummaryDto(
                user.id,
                user.firstName,
                user.lastName,
                AvatarColor.fromEmail(user.email));
    }

    public static RoleDto toRoleDto(RoleEntity role) {
        List<String> permissions = role.permissions.stream()
                .map(p -> p.name)
                .sorted()
                .toList();
        return new RoleDto(role.id, role.name, role.description, permissions);
    }

    public static PermissionDto toPermissionDto(PermissionEntity permission) {
        return new PermissionDto(permission.id, permission.name, permission.description);
    }

    public static List<String> roleNames(Set<RoleEntity> roles) {
        return roles.stream()
                .map(r -> r.name)
                .sorted()
                .toList();
    }

    public static List<String> permissionNames(Set<RoleEntity> roles) {
        return roles.stream()
                .flatMap(r -> r.permissions.stream())
                .map(p -> p.name)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<UserDto> toUserDtos(List<UserEntity> users) {
        return users.stream()
                .sorted(Comparator.comparing(u -> u.email, String.CASE_INSENSITIVE_ORDER))
                .map(DtoMapper::toUserDto)
                .toList();
    }
}
