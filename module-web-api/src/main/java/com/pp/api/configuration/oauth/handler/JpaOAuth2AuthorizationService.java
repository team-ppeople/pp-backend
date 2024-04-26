package com.pp.api.configuration.oauth.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.api.configuration.oauth.jackson.JwtClientAssertionOauth2AuthorizationServerJackson2Module;
import com.pp.api.entity.OauthFrameworkAuthorization;
import com.pp.api.repository.OauthFrameworkAuthorizationRepository;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

import static org.springframework.security.jackson2.SecurityJackson2Modules.getModules;
import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.SERVER_ERROR;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;
import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;
import static org.springframework.util.StringUtils.collectionToDelimitedString;
import static org.springframework.util.StringUtils.commaDelimitedListToSet;

/**
 * @see <a href="https://docs.spring.io/spring-authorization-server/reference/guides/how-to-jpa.html">spring authorization server referernce</a>
 */
@Component
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final OauthFrameworkAuthorizationRepository authorizationRepository;

    private final RegisteredClientRepository registeredClientRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModules(getModules(this.getClass().getClassLoader()))
            .registerModule(new OAuth2AuthorizationServerJackson2Module())
            .registerModule(new JwtClientAssertionOauth2AuthorizationServerJackson2Module());

    public JpaOAuth2AuthorizationService(
            OauthFrameworkAuthorizationRepository authorizationRepository,
            RegisteredClientRepository registeredClientRepository
    ) {
        Assert.notNull(authorizationRepository, "authorizationRepository cannot be null");
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
        this.authorizationRepository = authorizationRepository;
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.authorizationRepository.save(toEntity(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.authorizationRepository.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.authorizationRepository.findById(id).map(this::toObject).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        if (tokenType == null) {
            return this.authorizationRepository.findByAnyToken(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (STATE.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByState(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (CODE.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByAuthorizationCodeValue(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (ACCESS_TOKEN.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByAccessTokenValue(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (REFRESH_TOKEN.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByRefreshTokenValue(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (ID_TOKEN.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByOidcIdTokenValue(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (USER_CODE.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByUserCodeValue(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        if (DEVICE_CODE.equals(tokenType.getValue())) {
            return this.authorizationRepository.findByDeviceCodeValue(token)
                    .map(this::toObject)
                    .orElse(null);
        }

        return null;
    }

    private OAuth2Authorization toObject(OauthFrameworkAuthorization entity) {
        RegisteredClient registeredClient = this.registeredClientRepository.findById(entity.getRegisteredClientId());

        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + entity.getRegisteredClientId() + "' was not found in the RegisteredClientRepository."
            );
        }

        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(entity.getId())
                .principalName(entity.getPrincipalName())
                .authorizationGrantType(resolveAuthorizationGrantType(entity.getAuthorizationGrantType()))
                .authorizedScopes(commaDelimitedListToSet(entity.getAuthorizedScopes()))
                .attributes(attributes -> attributes.putAll(parseMap(entity.getAttributes())));

        if (entity.getState() != null) {
            builder.attribute(STATE, entity.getState());
        }

        if (entity.getAuthorizationCodeValue() != null) {
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    entity.getAuthorizationCodeValue(),
                    entity.getAuthorizationCodeIssuedAt(),
                    entity.getAuthorizationCodeExpiresAt()
            );

            builder.token(authorizationCode, metadata -> metadata.putAll(parseMap(entity.getAuthorizationCodeMetadata())));
        }

        if (entity.getAccessTokenValue() != null) {
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    BEARER,
                    entity.getAccessTokenValue(),
                    entity.getAccessTokenIssuedAt(),
                    entity.getAccessTokenExpiresAt(),
                    commaDelimitedListToSet(entity.getAccessTokenScopes())
            );

            builder.token(accessToken, metadata -> metadata.putAll(parseMap(entity.getAccessTokenMetadata())));
        }

        if (entity.getRefreshTokenValue() != null) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    entity.getRefreshTokenValue(),
                    entity.getRefreshTokenIssuedAt(),
                    entity.getRefreshTokenExpiresAt()
            );

            builder.token(refreshToken, metadata -> metadata.putAll(parseMap(entity.getRefreshTokenMetadata())));
        }

        if (entity.getOidcIdTokenValue() != null) {
            OidcIdToken idToken = new OidcIdToken(
                    entity.getOidcIdTokenValue(),
                    entity.getOidcIdTokenIssuedAt(),
                    entity.getOidcIdTokenExpiresAt(),
                    parseMap(entity.getOidcIdTokenClaims())
            );

            builder.token(idToken, metadata -> metadata.putAll(parseMap(entity.getOidcIdTokenMetadata())));
        }

        if (entity.getUserCodeValue() != null) {
            OAuth2UserCode userCode = new OAuth2UserCode(
                    entity.getUserCodeValue(),
                    entity.getUserCodeIssuedAt(),
                    entity.getUserCodeExpiresAt()
            );

            builder.token(userCode, metadata -> metadata.putAll(parseMap(entity.getUserCodeMetadata())));
        }

        if (entity.getDeviceCodeValue() != null) {
            OAuth2DeviceCode deviceCode = new OAuth2DeviceCode(
                    entity.getDeviceCodeValue(),
                    entity.getDeviceCodeIssuedAt(),
                    entity.getDeviceCodeExpiresAt()
            );

            builder.token(deviceCode, metadata -> metadata.putAll(parseMap(entity.getDeviceCodeMetadata())));
        }

        return builder.build();
    }

    private OauthFrameworkAuthorization toEntity(OAuth2Authorization authorization) {
        OauthFrameworkAuthorization entity = new OauthFrameworkAuthorization();

        entity.setId(authorization.getId());
        entity.setRegisteredClientId(authorization.getRegisteredClientId());
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        entity.setAuthorizedScopes(collectionToDelimitedString(authorization.getAuthorizedScopes(), ","));
        entity.setAttributes(writeMap(authorization.getAttributes()));
        entity.setState(authorization.getAttribute(STATE));

        Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);

        setTokenValues(
                authorizationCode,
                entity::setAuthorizationCodeValue,
                entity::setAuthorizationCodeIssuedAt,
                entity::setAuthorizationCodeExpiresAt,
                entity::setAuthorizationCodeMetadata
        );

        Token<OAuth2AccessToken> accessToken = authorization.getToken(OAuth2AccessToken.class);

        setTokenValues(
                accessToken,
                entity::setAccessTokenValue,
                entity::setAccessTokenIssuedAt,
                entity::setAccessTokenExpiresAt,
                entity::setAccessTokenMetadata
        );

        if (accessToken != null && accessToken.getToken().getScopes() != null) {
            entity.setAccessTokenScopes(collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
        }

        Token<OAuth2RefreshToken> refreshToken = authorization.getToken(OAuth2RefreshToken.class);

        setTokenValues(
                refreshToken,
                entity::setRefreshTokenValue,
                entity::setRefreshTokenIssuedAt,
                entity::setRefreshTokenExpiresAt,
                entity::setRefreshTokenMetadata
        );

        Token<OidcIdToken> oidcIdToken = authorization.getToken(OidcIdToken.class);

        setTokenValues(
                oidcIdToken,
                entity::setOidcIdTokenValue,
                entity::setOidcIdTokenIssuedAt,
                entity::setOidcIdTokenExpiresAt,
                entity::setOidcIdTokenMetadata
        );

        if (oidcIdToken != null) {
            entity.setOidcIdTokenClaims(writeMap(oidcIdToken.getClaims()));
        }

        Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);

        setTokenValues(
                userCode,
                entity::setUserCodeValue,
                entity::setUserCodeIssuedAt,
                entity::setUserCodeExpiresAt,
                entity::setUserCodeMetadata
        );

        Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);

        setTokenValues(
                deviceCode,
                entity::setDeviceCodeValue,
                entity::setDeviceCodeIssuedAt,
                entity::setDeviceCodeExpiresAt,
                entity::setDeviceCodeMetadata
        );

        return entity;
    }

    private void setTokenValues(
            Token<?> token,
            Consumer<String> tokenValueConsumer,
            Consumer<Instant> issuedAtConsumer,
            Consumer<Instant> expiresAtConsumer,
            Consumer<String> metadataConsumer
    ) {
        if (token == null) {
            return;
        }

        OAuth2Token oAuth2Token = token.getToken();

        tokenValueConsumer.accept(oAuth2Token.getTokenValue());
        issuedAtConsumer.accept(oAuth2Token.getIssuedAt());
        expiresAtConsumer.accept(oAuth2Token.getExpiresAt());
        metadataConsumer.accept(writeMap(token.getMetadata()));
    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private String writeMap(Map<String, Object> metadata) {
        try {
            return this.objectMapper.writeValueAsString(metadata);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        }

        if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        }

        if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }

        if (AuthorizationGrantType.DEVICE_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.DEVICE_CODE;
        }

        return new AuthorizationGrantType(authorizationGrantType);
    }

    @Aspect
    @Component
    public static class OAuth2AuthorizationServiceAspect {

        @AfterThrowing(
                pointcut = "execution(* com.pp.api.configuration.oauth.handler.JpaOAuth2AuthorizationService.*(..))",
                throwing = "exception"
        )
        public void afterThrowing(Exception exception) {
            if (!(exception instanceof OAuth2AuthenticationException)) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error(SERVER_ERROR),
                        exception
                );
            }
        }
    }

}
