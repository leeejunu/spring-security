package com.study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninRequestDto {
    private String username;
    private String password;

}
