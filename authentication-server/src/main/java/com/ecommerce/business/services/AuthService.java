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

    @Transactional(readOnly = true)
    public Mono<Boolean> validateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    @Transactional(readOnly = true)
    public String getUserEmailFromToken(String token) {
        return tokenProvider.getEmailFromToken(token);
    }

    @Transactional(readOnly = true)
    public boolean isAdmin(String email) {
        return userRepository.findByEmail(email)
            .map(user -> "admin@admin.com".equals(user.getEmail()))
            .orElse(false);
    }
}
