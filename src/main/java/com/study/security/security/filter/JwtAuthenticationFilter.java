package com.study.security.security.filter;

import com.study.security.entity.User;
import com.study.security.repository.UserRepository;
import com.study.security.security.jwt.JwtUtil;
import com.study.security.security.model.PrincipalUser;

import io.jsonwebtoken.Claims;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


//AuthenticationFilter 대체 함.
@Slf4j
@Component
public class JwtAuthenticationFilter implements Filter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        List<String> methods = List.of("POST", "PUT", "GET", "PATCH", "DELETE");
        if(!methods.contains(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authorization = request.getHeader("Authorization");
        log.info("Bearer 토큰 : {}", authorization);

        if (jwtUtil.isBearer(authorization)) {
            String accessToken = jwtUtil.removeBearer(authorization);
            try {
                Claims claims = jwtUtil.getClaims(accessToken);
                //토큰에서 Claims를 추출
                //이때 서명검증도 같이 진행
                //서명 위조나 만료 시 예외 발생
                String id = claims.getId();
                Long userId = Long.parseLong(id);

                Optional<User> findUser = userRepository.getUserById(userId);
                findUser.ifPresentOrElse((user) -> {
                    //DB에서 조회된 User 객체를 Spring Security 인증 객체 (PrincipalUser) 로 변환
                    //UserDetails
                    PrincipalUser principalUser = PrincipalUser.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .email(user.getEmail())
                            .build();

                    //UsernamePasswordAuthenticationToken
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, "", principalUser.getAuthorities());
                    //spring security의 인증 컨텍스트에 인증 객체 저장 => 이후 요청은 인증된 사용자로 간주
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("인증 완료");
                }, () -> {
                    throw new AuthenticationServiceException("인증 실패");
                });
            } catch (RuntimeException e) {
                e.fillInStackTrace();
            }

        }

        //인증 실패든 성공이든 필터링을 중단하지 않고 다음 필터로 넘어감
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("후처리");
    }


}
