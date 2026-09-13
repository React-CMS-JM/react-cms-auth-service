package com.reactcms.auth.dto;

import java.util.List;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        UserDto user,
        List<String> roles,
        List<String> permissions) {
}
