package com.study.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String username;
    @JsonIgnore
    private String password;
    private String email;

    //엔티티의 정의로 따지면 위배?
    //Mybatis에서는 엔티티가 DTO개념을 함께 가지고 있다.
    private List<UserRole> userRoles;
}
