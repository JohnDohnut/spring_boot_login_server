package com.dev.mywebserver.controller;


import com.dev.mywebserver.db.dto.User;
import com.dev.mywebserver.db.dao.UserRepository;
import com.dev.mywebserver.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserRepository userRepository;
    @GetMapping("/")
    public String getRootPage(){
        return "Hello World";
    }
    @GetMapping("/login")
    public String getLogin(){
        logger.debug("/login invoked");
        return "login page";
    }
    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/encrypt")
    public List<User> encrypt(){
        List<User> users = userRepository.findAll();
        for(User user : users){
            user.setAccPw(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        }
        return users;
    }
}
