package com.reactcms.auth.dto;

/** Slim user totals for the admin dashboard. */
public record UserStatsDto(long total, long banned) {}
