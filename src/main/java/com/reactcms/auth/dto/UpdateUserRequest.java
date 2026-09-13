package com.reactcms.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class UpdateUserRequest {

    @NotBlank
    @Email
    public String email;

    public String password;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @NotNull
    public List<Integer> roleIds;
}
