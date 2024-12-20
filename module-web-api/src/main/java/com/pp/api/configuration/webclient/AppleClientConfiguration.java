package com.pp.api.configuration.webclient;

import com.pp.api.configuration.webclient.property.AppleClientProperty;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.function.Function;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = AppleClientProperty.class)
public class AppleClientConfiguration {

    private final AppleClientProperty appleClientProperty;

    @Bean
    public ReactorClientHttpConnector appleClientHttpConnector(ReactorResourceFactory resourceFactory) {
        Function<HttpClient, HttpClient> mapper = httpClient -> httpClient.option(
                        CONNECT_TIMEOUT_MILLIS,
                        appleClientProperty.connectionTimeout()
                )
                .doOnConnected(
                        conn -> conn.addHandlerLast(
                                        new ReadTimeoutHandler(
                                                appleClientProperty.readTimeout(),
                                                MILLISECONDS
                                        )
                                )
                                .addHandlerLast(
                                        new WriteTimeoutHandler(
                                                appleClientProperty.writeTimeout(),
                                                MILLISECONDS
                                        ))
                );

        return new ReactorClientHttpConnector(resourceFactory, mapper);
    }

    @Bean
    public WebClient appleWebClient(@Qualifier(value = "appleClientHttpConnector") ReactorClientHttpConnector appleClientHttpConnector, ConversionService conversionService) {
        return WebClient.builder()
                .baseUrl(appleClientProperty.baseUrl())
                .clientConnector(appleClientHttpConnector)
                .defaultRequest(requestHeadersSpec ->
                        requestHeadersSpec.accept(APPLICATION_JSON)
                                .acceptCharset(UTF_8)
                )
                .build();
    }

}
