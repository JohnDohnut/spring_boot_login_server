package com.dev.mywebserver.controller;

import com.dev.mywebserver.db.dto.RefreshToken;
import com.dev.mywebserver.message.LoginResponse;
import com.dev.mywebserver.message.LoginRequest;
import com.dev.mywebserver.db.dto.User;
import com.dev.mywebserver.jwt.JwtService;
import com.dev.mywebserver.message.RefreshRequest;
import com.dev.mywebserver.security.JwtAuthenticationFilter;
import com.dev.mywebserver.service.AuthenticationService;
import com.dev.mywebserver.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequestDto) {
        logger.debug("login attempt : " + loginRequestDto.getId() + " " + loginRequestDto.getPassword());
        User authenticatedUser = authenticationService.authenticate(loginRequestDto);

        String jwtAccessToken = jwtService.generateToken(authenticatedUser);

        String jwtRefreshToken = jwtService.generateRefreshToken(authenticatedUser);

        RefreshToken refreshTokenDto = refreshTokenService.getRefreshTokenDtoByUser(authenticatedUser);

        if(refreshTokenDto == null) { refreshTokenDto = new RefreshToken();}

        refreshTokenDto.setUser(authenticatedUser);
        refreshTokenDto.setToken(jwtRefreshToken);

        refreshTokenService.save(refreshTokenDto);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setAccessToken(jwtAccessToken);
        loginResponse.setAccessTokenExpiresIn(jwtService.getJwtExpiration());

        loginResponse.setRefreshToken(jwtRefreshToken);
        loginResponse.setRefreshTokenExpiresIn(jwtService.getJwtRefreshExpiration());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshAuthentication(@RequestBody RefreshRequest refreshRequest){
        String refreshToken = refreshRequest.getJwtRefreshToken();
        if(!refreshToken.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        refreshToken = refreshToken.substring(7);

        User user = refreshTokenService.getUserByToken(refreshToken);
        if(user == null || !jwtService.isTokenValid(refreshToken, user)){

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        String accessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(newRefreshToken);

        loginResponse.setAccessTokenExpiresIn(jwtService.getJwtExpiration());
        loginResponse.setRefreshTokenExpiresIn(jwtService.getJwtRefreshExpiration());

        RefreshToken refreshTokenDto = refreshTokenService.getRefreshTokenDtoByToken(refreshToken);
        refreshTokenDto.setToken(newRefreshToken);
        refreshTokenService.save(refreshTokenDto);

        return ResponseEntity.ok(loginResponse);
    }

}
