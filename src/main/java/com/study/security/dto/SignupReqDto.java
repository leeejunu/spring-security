package com.study.security.dto;

import com.study.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
public class SignupReqDto {
    private String username;
    private String password;
    private String email;

    //비밀번호를 안전하게 암호화하고, 검증하는 역할
    //단방향 해시, 복호화 불가능
    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(this.username)
                .password(bCryptPasswordEncoder.encode(this.password))
                .email(this.email)
                .build();
    }
}
