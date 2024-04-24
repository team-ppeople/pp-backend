package com.pp.api.util;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public final class JwtAuthenticationUtil {

    public static Optional<Long> getAuthenticatedUserId() {
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

    public static void checkUserPermission(Long userId) {
        Long authenticatedUserId = getAuthenticatedUserId()
                .orElseThrow(() -> new AccessDeniedException("로그인하지 않은 유저입니다."));

        if (authenticatedUserId.equals(userId)) {
            return;
        }

        throw new AccessDeniedException("권한이 없는 유저입니다.");
    }

}
