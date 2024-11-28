package com.dev.mywebserver.db.dao;

import com.dev.mywebserver.db.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
