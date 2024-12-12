package com.ecommerce.business.services;


import com.ecommerce.business.db.entity.User;
import com.ecommerce.business.db.repository.UserRepository;
import com.ecommerce.business.exceptions.AuthException;
import com.ecommerce.gui.dto.LoginRequestDto;
import com.ecommerce.gui.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(SignUpRequestDto request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequestDto request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            // Generate JWT token
            return tokenProvider.generateToken(authentication);

        } catch (Exception e) {
            throw new AuthException("Invalid email/password combination");
        }
    }

    public Mono<Boolean> validateToken(String token) {
        return tokenProvider.validateToken(token);
    }


    public String getUserEmailFromToken(String token) {
        return tokenProvider.getEmailFromToken(token);
    }


    public Mono<Boolean> isAdmin(String email) {
        return Mono.just("admin@admin.com".equals(email));
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new AuthException("User not found"));
    }
}
