package com.pp.api.integration;

import com.pp.api.util.JwtTestUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.List;

public class WithMockUserJwtSecurityContextFactory implements WithSecurityContextFactory<WithMockUserJwt> {

    private static final String SCOPE_PREFIX = "SCOPE_";

    private final JwtTestUtil jwtTestUtil;

    public WithMockUserJwtSecurityContextFactory(JwtTestUtil jwtTestUtil) {
        this.jwtTestUtil = jwtTestUtil;
    }

    @Override
    public SecurityContext createSecurityContext(WithMockUserJwt withUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(createAuthentication(withUser));

        return context;
    }

    private String getSubject(WithMockUserJwt withUser) {
        return String.valueOf(withUser.value());
    }

    private List<SimpleGrantedAuthority> createGrantedAuthorities(WithMockUserJwt withUser) {
        return Arrays
                .stream(withUser.scopes())
                .map(scope -> new SimpleGrantedAuthority(SCOPE_PREFIX + scope))
                .toList();
    }

    private Authentication createAuthentication(WithMockUserJwt withUser) {
        return new JwtAuthenticationToken(
                jwtTestUtil.createJwt(getSubject(withUser)),
                createGrantedAuthorities(withUser),
                getSubject(withUser)
        );
    }

}