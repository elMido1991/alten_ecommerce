package com.ecommerce.gui.controllers;

import com.ecommerce.business.services.AuthService;
import com.ecommerce.gui.dto.LoginRequestDto;
import com.ecommerce.gui.dto.SignUpRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/account")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequestDto request) {
        authService.signup(request);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }
    
    @GetMapping("/validate")
    @PreAuthorize("isAuthenticated()")
    public Mono<ResponseEntity<Boolean>> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authService.validateToken(token)
                    .map(ResponseEntity::ok)
                    .onErrorResume(e -> {
                        log.error("Error validating token", e);
                        return Mono.just(ResponseEntity.ok(false));
                    });
        }
        return Mono.just(ResponseEntity.ok(false));
    }

    @GetMapping("/check_access")
    @PreAuthorize("isAuthenticated()")
    public Mono<ResponseEntity<Boolean>> checkAccess(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authService.isAdmin(authentication.getName())
                    .map(ResponseEntity::ok)
                    .onErrorResume(e -> Mono.just(ResponseEntity.ok(false)));
        }
        return Mono.just(ResponseEntity.ok(false));
    }




}