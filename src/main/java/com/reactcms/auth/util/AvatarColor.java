package com.reactcms.auth.util;

public final class AvatarColor {

    private static final String[] PALETTE = {
            "#6366f1", "#0ea5e9", "#8b5cf6", "#14b8a6", "#f59e0b",
            "#ef4444", "#22c55e", "#ec4899", "#64748b", "#06b6d4"
    };

    private AvatarColor() {
    }

    public static String fromEmail(String email) {
        if (email == null || email.isBlank()) {
            return PALETTE[0];
        }
        int hash = email.trim().toLowerCase().hashCode();
        int index = Math.floorMod(hash, PALETTE.length);
        return PALETTE[index];
    }
}
