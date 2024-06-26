package com.pp.api.configuration.webclient;

import com.pp.api.configuration.webclient.property.SlackClientProperty;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.function.Function;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = SlackClientProperty.class)
public class SlackClientConfiguration {

    private final SlackClientProperty slackClientProperty;

    @Bean
    public ReactorClientHttpConnector slackClientHttpConnector(ReactorResourceFactory resourceFactory) {
        Function<HttpClient, HttpClient> mapper = httpClient -> httpClient.option(
                        CONNECT_TIMEOUT_MILLIS,
                        slackClientProperty.connectionTimeout()
                )
                .doOnConnected(
                        conn -> conn.addHandlerLast(
                                        new ReadTimeoutHandler(
                                                slackClientProperty.readTimeout(),
                                                MILLISECONDS
                                        )
                                )
                                .addHandlerLast(
                                        new WriteTimeoutHandler(
                                                slackClientProperty.writeTimeout(),
                                                MILLISECONDS
                                        ))
                );

        return new ReactorClientHttpConnector(resourceFactory, mapper);
    }

    @Bean
    public WebClient slackWebClient(@Qualifier(value = "slackClientHttpConnector") ReactorClientHttpConnector slackClientHttpConnector) {
        return WebClient.builder()
                .defaultHeader(
                        AUTHORIZATION,
                        "Bearer " + slackClientProperty.token()
                )
                .baseUrl(slackClientProperty.baseUrl())
                .clientConnector(slackClientHttpConnector)
                .defaultRequest(requestHeadersSpec ->
                        requestHeadersSpec.accept(APPLICATION_JSON)
                                .acceptCharset(UTF_8)
                )
                .build();
    }

}
