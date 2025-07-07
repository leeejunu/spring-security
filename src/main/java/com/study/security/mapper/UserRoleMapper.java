package com.study.security.mapper;

import com.study.security.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int insert(UserRole userRole);
}
