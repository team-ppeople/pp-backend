package com.pp.api.configuration.oauth.provider;

import com.pp.api.configuration.oauth.converter.JwtClientAssertionOauth2ClientCredentialsAuthenticationToken;
import com.pp.api.configuration.oauth.processor.JwtClientAssertionOauth2UserRegisterProcessor;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.PRIVATE_KEY_JWT;
import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.*;
import static org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token.CLAIMS_METADATA_NAME;
import static org.springframework.security.oauth2.server.authorization.OAuth2Authorization.withRegisteredClient;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.REFRESH_TOKEN;

public final class JwtClientAssertionOauth2ClientCredentialsAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final Logger logger = getLogger(this.getClass());

    private final OAuth2AuthorizationService authorizationService;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private final JwtClientAssertionOauth2UserRegisterProcessor jwtClientAssertionOauth2UserRegisterProcessor;

    public JwtClientAssertionOauth2ClientCredentialsAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            JwtClientAssertionOauth2UserRegisterProcessor jwtClientAssertionOauth2UserRegisterProcessor
    ) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        Assert.notNull(jwtClientAssertionOauth2UserRegisterProcessor, "jwtClientAssertionOauth2UserRegisterProcessor cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.jwtClientAssertionOauth2UserRegisterProcessor = jwtClientAssertionOauth2UserRegisterProcessor;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtClientAssertionOauth2ClientCredentialsAuthenticationToken clientCredentialsAuthentication = (JwtClientAssertionOauth2ClientCredentialsAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(clientCredentialsAuthentication);

        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }

        if (!registeredClient.getAuthorizationGrantTypes().contains(CLIENT_CREDENTIALS)) {
            throw new OAuth2AuthenticationException(UNAUTHORIZED_CLIENT);
        }

        if (!clientPrincipal.getClientAuthenticationMethod().equals(PRIVATE_KEY_JWT)) {
            throw new OAuth2AuthenticationException(UNAUTHORIZED_CLIENT);
        }

        Set<String> authorizedScopes = Set.of();

        if (!CollectionUtils.isEmpty(clientCredentialsAuthentication.getScopes())) {
            Set<String> unauthorizedScopes = clientCredentialsAuthentication.getScopes()
                    .stream()
                    .filter(requestedScope -> !registeredClient.getScopes().contains(requestedScope))
                    .collect(toSet());

            if (!CollectionUtils.isEmpty(unauthorizedScopes)) {
                throw new OAuth2AuthenticationException(INVALID_SCOPE);
            }

            authorizedScopes = new LinkedHashSet<>(clientCredentialsAuthentication.getScopes());
        }

        try {
            jwtClientAssertionOauth2UserRegisterProcessor.process(clientCredentialsAuthentication);
        } catch (Exception e) {
            logger.error(
                    "Oauth 인증 로그인 유저 등록 처리 실패",
                    e
            );

            if (e instanceof OAuth2AuthenticationException oauth2Exception) {
                throw oauth2Exception;
            }

            throw new OAuth2AuthenticationException(SERVER_ERROR);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Validated token request parameters");
        }

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .authorizationGrant(clientCredentialsAuthentication);

        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(ACCESS_TOKEN).build();

        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);

        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(
                    SERVER_ERROR,
                    "The token generator failed to generate the access token.",
                    ERROR_URI
            );

            throw new OAuth2AuthenticationException(error);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Generated access token");
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                BEARER,
                generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(),
                tokenContext.getAuthorizedScopes()
        );

        OAuth2Authorization.Builder authorizationBuilder = withRegisteredClient(registeredClient)
                .principalName(clientPrincipal.getName())
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .authorizedScopes(authorizedScopes)
                .attribute(
                        Principal.class.getName(),
                        clientPrincipal
                );

        if (generatedAccessToken instanceof ClaimAccessor claimAccessor) {
            authorizationBuilder.token(
                    accessToken,
                    metadata -> metadata.put(
                            CLAIMS_METADATA_NAME,
                            claimAccessor.getClaims()
                    )
            );
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        OAuth2RefreshToken refreshToken = null;

        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            tokenContext = tokenContextBuilder.tokenType(REFRESH_TOKEN).build();

            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);

            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(
                        SERVER_ERROR,
                        "The token generator failed to generate the refresh token.",
                        ERROR_URI
                );

                throw new OAuth2AuthenticationException(error);
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated refresh token");
            }

            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        OAuth2Authorization authorization = authorizationBuilder.build();

        this.authorizationService.save(authorization);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Saved authorization");
            this.logger.trace("Authenticated token request");
        }

        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient,
                clientPrincipal,
                accessToken,
                refreshToken
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtClientAssertionOauth2ClientCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        if (
                authentication.getPrincipal() instanceof OAuth2ClientAuthenticationToken clientAuthentication &&
                        clientAuthentication.isAuthenticated()
        ) {
            return clientAuthentication;
        }

        throw new OAuth2AuthenticationException(INVALID_CLIENT);
    }

}
