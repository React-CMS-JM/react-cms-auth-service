package com.reactcms.auth.dto;

import java.time.Instant;
import java.util.List;

public record UserDto(
        String id,
        String email,
        String firstName,
        String lastName,
        String avatarColor,
        boolean isBanned,
        String banReason,
        List<Integer> roleIds,
        Instant createdAt,
        Instant updatedAt) {
}
