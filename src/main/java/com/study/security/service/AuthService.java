package com.study.security.service;

import com.study.security.dto.ApiRespDto;
import com.study.security.dto.SigninRequestDto;
import com.study.security.dto.SignupReqDto;
import com.study.security.entity.User;
import com.study.security.entity.UserRole;
import com.study.security.repository.UserRepository;
import com.study.security.repository.UserRoleRepository;
import com.study.security.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRoleRepository userRoleRepository;

    public ApiRespDto<?> addUser(SignupReqDto signupReqDto) {
        Optional<User> user = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));
        UserRole userRole = UserRole.builder()
                .userId(user.get().getUserId())
                .roleId(3L)
                .build();

        userRoleRepository.addUserRole(userRole);

        return new ApiRespDto<>("success", "회원가입 성공", user);
    }

    public ApiRespDto<?> signin(SigninRequestDto signinRequestDto) {
        Optional<User> findUser = userRepository.getUserByUsername(signinRequestDto.getUsername());
        if (findUser.isEmpty()) {
            return new ApiRespDto<>("failed", "사용자 정보를 확인해주세요.", null);
        }

        User user = findUser.get();
        if (!bCryptPasswordEncoder.matches(signinRequestDto.getPassword(), user.getPassword())) {
            return new ApiRespDto<>("failed", "사용자 정보를 확인해주세요.", null);
        }

        String token = jwtUtil.generateAccessToken(user.getUserId().toString());
        return new ApiRespDto<>("success", "로그인 성공", token);
    }
}
