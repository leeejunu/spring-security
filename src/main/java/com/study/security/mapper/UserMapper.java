package com.study.security.mapper;

import com.study.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int addUser(User user);
}
