package com.pp.api.util;

import com.pp.api.exception.NotAuthenticatedUserAccessException;
import com.pp.api.exception.NotPermittedUserAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public final class JwtAuthenticationUtil {

    public static Optional<Long> getAuthenticatedUserIdIfPresent() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return Optional.of(Long.parseLong(jwt.getSubject()));
        }

        return Optional.empty();
    }

    public static Long getAuthenticatedUserId() {
        return getAuthenticatedUserIdIfPresent()
                .orElseThrow(NotAuthenticatedUserAccessException::new);
    }

    public static void checkUserPermission(Long userId) {
        if (isAuthenticatedUser(userId)) {
            return;
        }

        throw new NotPermittedUserAccessException();
    }

    public static boolean isAuthenticatedUser(Long userId) {
        Long authenticatedUserId = getAuthenticatedUserId();

        if (authenticatedUserId.equals(userId)) {
            return true;
        }

        return false;
    }

}
