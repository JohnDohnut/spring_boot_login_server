package com.dev.mywebserver.db.dao;

import com.dev.mywebserver.db.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    User findUserByAccId(String acc_id);
    Optional<User> findUserByEmail(String email);

}

