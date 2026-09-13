package com.reactcms.auth.dto;

import java.util.List;

public record MeResponse(
        UserDto user,
        List<String> roles,
        List<String> permissions) {
}
