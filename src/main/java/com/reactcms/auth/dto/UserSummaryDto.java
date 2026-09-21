package com.reactcms.auth.dto;

/** Slim user fields for batch lookup (dashboard / author labels). */
public record UserSummaryDto(String id, String firstName, String lastName, String avatarColor) {}
