package com.study.security.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.security.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ContextHolder에 유저 정보 저장
 */
@Data
@Builder
public class PrincipalUser implements UserDetails {

    private Long userId;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private List<UserRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName()))
                .collect(Collectors.toList());
    }
}
