package com.dev.mywebserver.message;

import jakarta.persistence.Entity;
import lombok.Getter;


@Getter
public class RefreshRequest {
    private String jwtRefreshToken;
}
