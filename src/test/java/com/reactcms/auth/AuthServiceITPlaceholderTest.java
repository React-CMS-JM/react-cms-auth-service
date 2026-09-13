package com.reactcms.auth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * DB-backed QuarkusTest suites are skipped: schema is owned by MySQL SQL scripts.
 * Run the service against local MySQL to verify endpoints.
 */
@Disabled("Requires MySQL react_cms schema; skipped for default mvn test")
class AuthServiceITPlaceholderTest {

    @Test
    void placeholder() {
        // intentionally empty
    }
}
