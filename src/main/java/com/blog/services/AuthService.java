package com.blog.services;

import com.blog.payloads.LoginDTO;
import com.blog.payloads.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO loginDTO);

}
