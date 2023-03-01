package com.blog.services;

import com.blog.payloads.JWTAuthResponse;
import com.blog.payloads.LoginDTO;
import com.blog.payloads.RegisterDTO;

public interface AuthService {
    JWTAuthResponse login(LoginDTO loginDTO);
    String register(RegisterDTO loginDTO);

}
