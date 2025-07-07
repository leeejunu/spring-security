package com.study.security.mapper;

import com.study.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> addUser(User user);

    Optional<User> getUserByUserId(Long userId);

    Optional<User> getUserByUsername(String username);
}
