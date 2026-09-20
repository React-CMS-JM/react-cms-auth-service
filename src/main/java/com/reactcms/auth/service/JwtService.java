package com.reactcms.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collection;
import java.util.Set;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    /** Raw HMAC secret (UTF-8). Same value content/courses use to verify tokens. */
    @ConfigProperty(name = "react-cms.jwt.hmac-secret")
    String hmacSecret;

    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan", defaultValue = "3600")
    long lifespanSeconds;

    /** Stable kid stamped on signed tokens (matches smallrye.jwt.token.kid on verifying services). */
    @ConfigProperty(name = "smallrye.jwt.new-token.kid", defaultValue = "react-cms-hs256")
    String keyId;

    public String generateToken(String userId, String email, Collection<String> roleNames,
            Collection<String> permissions) {
        return Jwt.issuer(issuer)
                .subject(userId)
                .upn(email)
                .groups(Set.copyOf(roleNames))
                .claim("permissions", permissions)
                .jws()
                .keyId(keyId)
                .signWithSecret(hmacSecret);
    }

    public long expiresInSeconds() {
        return lifespanSeconds;
    }
}
