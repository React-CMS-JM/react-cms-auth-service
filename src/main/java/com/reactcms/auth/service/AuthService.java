package com.reactcms.auth.service;

import com.reactcms.auth.dto.CreateUserRequest;
import com.reactcms.auth.dto.LoginRequest;
import com.reactcms.auth.dto.LoginResponse;
import com.reactcms.auth.dto.MeResponse;
import com.reactcms.auth.dto.UpdateUserRequest;
import com.reactcms.auth.dto.UserDto;
import com.reactcms.auth.entity.RoleEntity;
import com.reactcms.auth.entity.UserEntity;
import com.reactcms.auth.util.DtoMapper;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class AuthService {

    private final JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        UserEntity user = UserEntity.findByEmail(request.email)
                .orElseThrow(() -> unauthorized("Invalid email or password"));

        if (!BcryptUtil.matches(request.password, user.passwordHash)) {
            throw unauthorized("Invalid email or password");
        }
        if (Boolean.TRUE.equals(user.isBanned)) {
            throw unauthorized("User is banned");
        }

        List<String> roles = DtoMapper.roleNames(user.roles);
        List<String> permissions = DtoMapper.permissionNames(user.roles);
        String token = jwtService.generateToken(user.id, user.email, roles, permissions);

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.expiresInSeconds(),
                DtoMapper.toUserDto(user),
                roles,
                permissions);
    }

    public MeResponse me(String userId) {
        UserEntity user = UserEntity.<UserEntity>findByIdOptional(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return new MeResponse(
                DtoMapper.toUserDto(user),
                DtoMapper.roleNames(user.roles),
                DtoMapper.permissionNames(user.roles));
    }

    public List<UserDto> listUsers() {
        return DtoMapper.toUserDtos(UserEntity.listAll());
    }

    public UserDto getUser(String id) {
        return DtoMapper.toUserDto(requireUser(id));
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        String email = request.email.trim().toLowerCase();
        if (UserEntity.findByEmail(email).isPresent()) {
            throw new BadRequestException("Email already in use");
        }

        UserEntity user = new UserEntity();
        user.id = UUID.randomUUID().toString();
        user.email = email;
        user.passwordHash = BcryptUtil.bcryptHash(request.password);
        user.firstName = request.firstName.trim();
        user.lastName = request.lastName.trim();
        user.isBanned = false;
        user.roles = resolveRoles(request.roleIds);
        user.persist();
        return DtoMapper.toUserDto(user);
    }

    @Transactional
    public UserDto updateUser(String id, UpdateUserRequest request) {
        UserEntity user = requireUser(id);
        String email = request.email.trim().toLowerCase();
        UserEntity.findByEmail(email).ifPresent(existing -> {
            if (!existing.id.equals(id)) {
                throw new BadRequestException("Email already in use");
            }
        });

        user.email = email;
        user.firstName = request.firstName.trim();
        user.lastName = request.lastName.trim();
        if (request.password != null && !request.password.isBlank()) {
            user.passwordHash = BcryptUtil.bcryptHash(request.password);
        }
        user.roles = resolveRoles(request.roleIds);
        return DtoMapper.toUserDto(user);
    }

    @Transactional
    public UserDto banUser(String id, String reason) {
        UserEntity user = requireUser(id);
        user.isBanned = true;
        user.banReason = reason;
        return DtoMapper.toUserDto(user);
    }

    @Transactional
    public UserDto unbanUser(String id) {
        UserEntity user = requireUser(id);
        user.isBanned = false;
        user.banReason = null;
        return DtoMapper.toUserDto(user);
    }

    private static NotAuthorizedException unauthorized(String message) {
        return new NotAuthorizedException(message, Response.status(Response.Status.UNAUTHORIZED).build());
    }

    private UserEntity requireUser(String id) {
        return UserEntity.<UserEntity>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    private Set<RoleEntity> resolveRoles(List<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<RoleEntity> roles = new HashSet<>();
        for (Integer roleId : roleIds) {
            RoleEntity role = RoleEntity.findById(roleId);
            if (role == null) {
                throw new BadRequestException("Unknown role id: " + roleId);
            }
            roles.add(role);
        }
        return roles;
    }
}
