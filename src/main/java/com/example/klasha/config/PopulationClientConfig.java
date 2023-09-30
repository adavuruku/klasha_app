package com.example.klasha.config;


import com.example.klasha.api.clients.spring.PopulationClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
@Slf4j
@RefreshScope
public class PopulationClientConfig {

    @Value("${population.base_url}")
    private String populationServiceBaseUrl;

    @Value("${population.connection.time:30}")
    private int populationConnectionTimeout;

    @Bean
    @RefreshScope
    public PopulationClient populationServiceClient(){

        HttpClient httpClient = HttpClient.create()
                .proxyWithSystemProperties()
                .compress(true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL, StandardCharsets.UTF_8);
        httpClient.warmup().block();
        WebClient webClient = WebClient
                .builder()
                .baseUrl(populationServiceBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultStatusHandler(HttpStatusCode::isError, resp -> {
                    log.error("Error while calling endpoint with status code {}", resp.statusCode());
                    return resp.bodyToMono(String.class).flatMap(err -> {
                        log.error("Error while calling transfer service {} ", err);
                        return Mono.error(new RuntimeException());
                    });
                })
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(populationConnectionTimeout))
                .build();
        return factory.createClient(PopulationClient.class);
    }
}
