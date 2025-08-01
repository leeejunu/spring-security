package com.study.security.config;

import com.study.security.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * corsConfigurationSource() 설정은 spring security 에서
     * CORS(Cross-Origin Resource Sharing)를 처리하기 위한 설정
     * CORS
     * 브라우저가 보안상 다른 도메인의 리소스 요청을 제한하는 정책
     * 기본적으로 브라우저는 같은 출처(Same-Origin)만 허용한다.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        //요청을 보내는 쪽의 도메인(사이트 주소)을 허용
        corsConfiguration.addAllowedOriginPattern(CorsConfiguration.ALL);
        //요청을 보내는 쪽에서 Request, Response Header 정보에 대한 제약을 허용
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //요청을 보내는 쪽의 메소드 (GET, POST, PUT, DELETE, OPTION 등)
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);

        //요청 URL (/user/get)에 대한 CORS 설정 적용을 위해 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //모든 URL(/**)에 대한 위에서 설정한 CORS 정책을 적용
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults()); //위에서 만든 cors security에 설정을 적용
        //csrf: 사용자가 의도하지 않은 요청을 공격자가 유도해서 서버에 전달하도록 하는 공격
        //jwt 방식 또는 무상태(Stateless) 인증이기 때문에
        //세션이 없고, 쿠키도 안 쓰고, 토큰 기반이기 때문에 CSRF 공격 자체가 성립되지 않는다.
        return http.csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable()) //서버 사이드 렌더링 로그인 방식 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) //http 프로토콜 기본 로그인 방식 비활성화
                .logout(logout -> logout.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/test", "/auth/signup", "/auth/signin").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();

    }
}
