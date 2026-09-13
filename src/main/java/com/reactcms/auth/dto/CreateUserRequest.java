package com.reactcms.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateUserRequest {

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @NotNull
    public List<Integer> roleIds;
}
