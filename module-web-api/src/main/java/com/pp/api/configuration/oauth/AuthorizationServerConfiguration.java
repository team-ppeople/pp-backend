package com.pp.api.configuration.oauth;

import com.pp.api.configuration.oauth.converter.JwtClientAssertionOAuth2ClientCredentialsAuthenticationConverter;
import com.pp.api.configuration.oauth.converter.RefreshTokenEndpointPublicClientAuthenticationConverter;
import com.pp.api.configuration.oauth.converter.RevacationTokenEndpointPublicClientAuthenticationConverter;
import com.pp.api.configuration.oauth.processor.JwtClientAssertionOauth2UserRegisterProcessor;
import com.pp.api.configuration.oauth.provider.JwtClientAssertionOauth2ClientCredentialsAuthenticationProvider;
import com.pp.api.configuration.oauth.provider.NoneClientAuthenticationMethodPublicClientAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionDecoderFactory;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Collections.singletonList;

@Configuration
public class AuthorizationServerConfiguration {

    @Bean
    @SuppressWarnings("removal")
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity httpSecurity,
            RegisteredClientRepository registeredClientRepository,
            JwtClientAssertionOauth2UserRegisterProcessor jwtClientAssertionOauth2UserRegisterProcessor,
            AuthorizationServerSettings authorizationServerSettings
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer()
                .tokenEndpoint((tokenEndpoint) -> tokenEndpoint.accessTokenRequestConverter(
                                new JwtClientAssertionOAuth2ClientCredentialsAuthenticationConverter()
                        )
                );

        httpSecurity
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().authenticated())
                .apply(authorizationServerConfigurer);

        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .clientAuthentication(clientAuthentication -> {
                    clientAuthentication.authenticationConverter(
                            new RefreshTokenEndpointPublicClientAuthenticationConverter(
                                    authorizationServerSettings.getTokenEndpoint()
                            )
                    );
                    clientAuthentication.authenticationConverter(
                            new RevacationTokenEndpointPublicClientAuthenticationConverter(
                                    authorizationServerSettings.getTokenRevocationEndpoint()
                            )
                    );
                    clientAuthentication.authenticationProvider(new NoneClientAuthenticationMethodPublicClientAuthenticationProvider(registeredClientRepository));
                    clientAuthentication.authenticationProviders(configureJwtClientAssertionValidator());
                });

        SecurityFilterChain securityFilterChain = httpSecurity.build();

        addJwtClientAssertionOauth2ClientCredentialsAuthenticationProvider(
                httpSecurity,
                jwtClientAssertionOauth2UserRegisterProcessor
        );

        return securityFilterChain;
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings
                .builder()
                .build();
    }

    @Bean
    public JwtDecoderFactory<RegisteredClient> jwtDecoderFactory() {
        JwtClientAssertionDecoderFactory jwtDecoderFactory = new JwtClientAssertionDecoderFactory();

        Function<RegisteredClient, OAuth2TokenValidator<Jwt>> jwtValidatorFactory = (registeredClient) ->
                new DelegatingOAuth2TokenValidator<>(
                        new JwtClaimValidator<>("iss", Objects::nonNull),
                        new JwtClaimValidator<>("sub", Objects::nonNull),
                        new JwtClaimValidator<>("aud", Objects::nonNull),
                        new JwtClaimValidator<>("exp", Objects::nonNull),
                        new JwtTimestampValidator()
                );

        jwtDecoderFactory.setJwtValidatorFactory(jwtValidatorFactory);

        return jwtDecoderFactory;
    }

    private Consumer<List<AuthenticationProvider>> configureJwtClientAssertionValidator() {
        return (authenticationProviders) -> authenticationProviders.forEach(authenticationProvider -> {
            if (authenticationProvider instanceof JwtClientAssertionAuthenticationProvider jwtClientAssertionAuthenticationProvider) {
                jwtClientAssertionAuthenticationProvider.setJwtDecoderFactory(jwtDecoderFactory());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void addJwtClientAssertionOauth2ClientCredentialsAuthenticationProvider(
            HttpSecurity httpSecurity,
            JwtClientAssertionOauth2UserRegisterProcessor jwtClientAssertionOauth2UserRegisterProcessor
    ) {
        JwtClientAssertionOauth2ClientCredentialsAuthenticationProvider provider = new JwtClientAssertionOauth2ClientCredentialsAuthenticationProvider(
                httpSecurity.getSharedObject(OAuth2AuthorizationService.class),
                httpSecurity.getSharedObject(OAuth2TokenGenerator.class),
                jwtClientAssertionOauth2UserRegisterProcessor
        );

        httpSecurity.authenticationProvider(provider);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedHeaders(singletonList("*"));
            configuration.setAllowedMethods(singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedOriginPatterns(List.of("https://team-ppeople.github.io"));

            return configuration;
        };
    }

}
