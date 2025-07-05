package com.study.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
}
