package com.dev.mywebserver.service;

import com.dev.mywebserver.db.dao.UserRepository;
import com.dev.mywebserver.db.dao.UserRoleRepository;
import com.dev.mywebserver.db.dto.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRepository userRepository, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<String> getUserRoles(String userId){
        User user = userRepository.findUserByAccId(userId);
        return userRoleRepository.findUserRolesByUser(userRepository.findUserByAccId(userId))
                .stream()
                .map(userRole -> userRole.getRole().getRole())
                .collect(Collectors.toList());
    }
}
