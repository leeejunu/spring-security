package com.study.security.mapper;

import com.study.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int addUser(User user);

    Optional<User> getUserByUserId(Long userId);
}
