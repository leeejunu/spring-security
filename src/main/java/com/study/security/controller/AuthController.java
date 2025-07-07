package com.study.security.controller;

import com.study.security.dto.SigninRequestDto;
import com.study.security.dto.SignupReqDto;
import com.study.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok().body(authService.addUser(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequestDto signinRequestDto) {
        return ResponseEntity.ok().body(authService.signin(signinRequestDto));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal() {
        log.info("contextHolder : {}", SecurityContextHolder.getContext());
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
}
