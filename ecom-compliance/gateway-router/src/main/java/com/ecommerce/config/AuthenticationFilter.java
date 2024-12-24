package com.ecommerce.config;

import com.ecommerce.controllers.AuthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter {



    private final AuthServiceClient authServiceClient;

    private static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/token",
            "/api/v1/auth/account",
            "/eureka",
            "/swagger-ui",
            "/v3/api-docs"
    );

    public AuthenticationFilter(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Skip authentication for open endpoints
        if (openApiEndpoints.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }


        // Check for Authorization header
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header");
        }

        String token = authHeader.substring(7);

        //check token validity and admin access
        return Mono.just(token)
                // First validate token
                .flatMap(t -> authServiceClient.validateToken(t)
                        .flatMap(isValidToken -> {
                            if (Boolean.TRUE.equals(isValidToken)) {
                                return Mono.just(t); // Pass token to next step if valid
                            }
                            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
                        }))
                // Then check access
                .flatMap(t -> authServiceClient.checkAccess(t)
                        .flatMap(isValidAccess -> {
                            if (Boolean.TRUE.equals(isValidAccess)) {
                                return chain.filter(exchange);
                            }
                            return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied to this resource"));
                        }));
    }



}
