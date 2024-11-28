package com.dev.mywebserver.db.dao;
import com.dev.mywebserver.db.dto.User;
import com.dev.mywebserver.db.dto.UserRole;
import com.dev.mywebserver.db.dto.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findUserRolesByUser(User user);
}
