package com.dev.mywebserver.service;

import com.dev.mywebserver.db.dao.RefreshTokenRepository;
import com.dev.mywebserver.db.dto.RefreshToken;
import com.dev.mywebserver.db.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public User getUserByToken(String token){
        RefreshToken refreshTokenDto = refreshTokenRepository.findRefreshTokenByToken(token);
        if(refreshTokenDto == null) return null;

        return refreshTokenDto.getUser();
    }

    public RefreshToken getRefreshTokenDtoByToken(String token){
        return refreshTokenRepository.findRefreshTokenByToken(token);
    }

    public RefreshToken getRefreshTokenDtoByUser(User user){
        return refreshTokenRepository.findRefreshTokenByUser(user);
    }

    public void save(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }


}
