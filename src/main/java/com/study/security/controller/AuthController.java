package com.study.security.controller;

import com.study.security.dto.SignupReqDto;
import com.study.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
