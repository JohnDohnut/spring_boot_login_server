package com.dev.mywebserver.db.dao;

import com.dev.mywebserver.db.dto.RefreshToken;
import com.dev.mywebserver.db.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    public RefreshToken findRefreshTokenByToken(String token);
    public RefreshToken findRefreshTokenByUser(User user);

}
