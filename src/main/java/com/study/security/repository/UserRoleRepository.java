package com.study.security.repository;

import com.study.security.entity.UserRole;
import com.study.security.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRoleRepository {

    private final UserRoleMapper userRoleMapper;

    public Optional<UserRole> addUserRole(UserRole userRole) {
        return userRoleMapper.insert(userRole) < 1 ? Optional.empty() : Optional.of(userRole);
    }


}
