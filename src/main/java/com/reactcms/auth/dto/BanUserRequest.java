package com.reactcms.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class BanUserRequest {

    @NotBlank
    public String reason;
}
