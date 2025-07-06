package com.study.security.repository;

import com.study.security.entity.User;
import com.study.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userMapper.getUserByUserId(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
