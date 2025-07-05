package com.study.security.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key KEY;

    public JwtUtil(@Value(value = "${jwt.secret}") String secretKey) {
        KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateAccessToken(String id) {
        return Jwts.builder()
                .subject("AccessToken") //토큰의 용도를 설명하는 식별자 역할
                .id(id) //토큰에 고유한 식별자를 부여(사용자 ID, 이메일) => 나중에 토큰 무효화나 사용자 조회할때 사용
                .expiration(new Date(new Date().getTime() + (1000L * 60L * 60L * 24L * 30L))) //토큰의 만료기간 30일 뒤
                .signWith(KEY) //토큰에 서명을 적용
                .compact() //설정한 JWT 내용을 바탕으로 최종적으로 문자열 형태의 JWT생성
    }
}
