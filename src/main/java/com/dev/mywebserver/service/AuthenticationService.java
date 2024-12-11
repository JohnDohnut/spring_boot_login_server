package com.dev.mywebserver.service;

import com.dev.mywebserver.db.dao.UserRepository;
import com.dev.mywebserver.message.LoginRequest;
import com.dev.mywebserver.db.dto.User;
import com.dev.mywebserver.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);



    public User authenticate(LoginRequest input) {
        User user = userRepository.findUserByEmail(input.getId());
        logger.debug("User : " + user.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getId(),
                        input.getPassword()
                )
        );

        return user;
    }
}
