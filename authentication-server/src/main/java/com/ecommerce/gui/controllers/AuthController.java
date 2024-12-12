package com.ecommerce.gui.controllers;

import com.ecommerce.business.services.AuthService;
import com.ecommerce.gui.dto.LoginRequestDto;
import com.ecommerce.gui.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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
    public Mono<ResponseEntity<Boolean>> validateToken(@RequestParam String token) {
        return authService.validateToken(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}