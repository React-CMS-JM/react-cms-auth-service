package com.reactcms.auth.dto;

import java.util.List;

public record RoleDto(
        Integer id,
        String name,
        String description,
        List<String> permissions) {
}
