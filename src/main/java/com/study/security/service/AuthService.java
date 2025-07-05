package com.study.security.service;

import com.study.security.dto.ApiRespDto;
import com.study.security.dto.SignupReqDto;
import com.study.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> addUser(SignupReqDto signupReqDto) {
        int result = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
        return new ApiRespDto<>("success", "회원가입 성공", result);
    }
}
