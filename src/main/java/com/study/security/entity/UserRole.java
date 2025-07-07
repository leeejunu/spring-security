package com.study.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    private Long userRoleId;
    private Long userId;
    private Long roleId;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    private Role role;
}
