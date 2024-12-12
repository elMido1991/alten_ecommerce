package com.ecommerce.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceClient {

    private final WebClient.Builder webClientBuilder;
    private static final String AUTHENTICATION_SERVER_URL = "lb://authentication-server";

    private WebClient createWebClient() {
        return webClientBuilder
                .baseUrl(AUTHENTICATION_SERVER_URL)
                .build();
    }

    public Mono<Boolean> validateToken(String token) {
        return  createWebClient()
                .get()
                .uri("/api/v1/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.error("Token validation failed with status: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Token validation failed"));
                })
                .bodyToMono(Boolean.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> !(throwable instanceof RuntimeException)))
                .doOnError(error -> log.error("Error validating token: {}", error.getMessage()))
                .onErrorReturn(false);
    }

    public Mono<Boolean> checkAccess(String token) {
        return  createWebClient()
                .get()
                .uri("/api/v1/auth/check_access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Permission denied to this ressource")))
                .bodyToMono(Boolean.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> !(throwable instanceof RuntimeException)))
                .doOnError(error -> log.error("Error checking Access: {}", error.getMessage()))
                .onErrorReturn(false);
    }

}