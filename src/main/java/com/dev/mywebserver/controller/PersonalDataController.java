package com.dev.mywebserver.controller;

import com.dev.mywebserver.db.dao.UserRepository;
import com.dev.mywebserver.db.dto.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personal")
public class PersonalDataController {
    private final UserRepository userRepository;

    @GetMapping("/myInfo")
    public User getMyInfo() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User result = userRepository
                .findUserByAccId(userId);
        return result;
    }

    @GetMapping("/sayHi")
    public String sayHi(){
        return "Hi";
    }

}