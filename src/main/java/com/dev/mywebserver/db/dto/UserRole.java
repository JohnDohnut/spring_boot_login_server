package com.dev.mywebserver.db.dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class) // 복합 키 클래스 설정
@Getter
@Setter
public class UserRole {

    @Id // 복합 키의 첫 번째 필드
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id // 복합 키의 두 번째 필드
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}

