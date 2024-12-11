package com.dev.mywebserver.message;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {
    private String id;

    private String password;

    // getters and setters here...
}