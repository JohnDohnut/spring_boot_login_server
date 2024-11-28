package com.dev.mywebserver.db.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {
    private Long user; // User ID
    private Long role; // Role ID

    // 기본 생성자
    public UserRoleId() {}

    public UserRoleId(Long user, Long role) {
        this.user = user;
        this.role = role;
    }

    // Getters and Setters, equals, hashCode
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
