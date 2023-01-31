package com.blog.controllers;

import com.blog.payloads.LoginDTO;
import com.blog.payloads.RegisterDTO;
import com.blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<String> login (@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(this.authService.login(loginDTO));
    }

    @PostMapping(value ={"/register", "/sign-up"})
    public ResponseEntity<String> register (@Valid @RequestBody RegisterDTO registerDTO) {
        return new ResponseEntity<>(this.authService.register(registerDTO), HttpStatus.CREATED);
    }

}
