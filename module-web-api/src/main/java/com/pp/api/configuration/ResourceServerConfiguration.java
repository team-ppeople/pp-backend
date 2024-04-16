package com.pp.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableMethodSecurity
@Configuration
public class ResourceServerConfiguration {

    @Bean
    @Order(value = HIGHEST_PRECEDENCE + 1)
    public SecurityFilterChain resourceServerSecurityFilterChain(
            HttpSecurity httpSecurity,
            CorsConfigurationSource corsConfigurationSource,
            ObjectMapper objectMapper
    ) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2ResourceServer -> {
                    oauth2ResourceServer.jwt(withDefaults());
                    oauth2ResourceServer.accessDeniedHandler(
                            new CustomOauth2ResourceServerAccessDeniedHandler(objectMapper)
                    );
                    oauth2ResourceServer.authenticationEntryPoint(
                            new CustomOauth2ResourceServerAuthenticationEntryPoint(objectMapper)
                    );
                })
                .build();
    }

}
