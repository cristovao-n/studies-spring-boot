package com.blog.services.impl;

import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.DataConflictException;
import com.blog.payloads.LoginDTO;
import com.blog.payloads.RegisterDTO;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl (AuthenticationManager authenticationManager,
                            UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login (LoginDTO loginDTO) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged-in successfully.";
    }

    @Override
    public String register (RegisterDTO registerDTO) {
        Map<String, String> fieldErrors = new HashMap<>();
        if (this.userRepository.existsByUsername(registerDTO.getUsername())) {
            fieldErrors.put("username", "This username is already taken");
        }

        if (this.userRepository.existsByEmail(registerDTO.getEmail())) {
            fieldErrors.put("email", "This email is already taken");
        }
        boolean hasDataConflict = fieldErrors.size() > 0;
        if (hasDataConflict) {
            throw new DataConflictException(fieldErrors);
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(this.passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = this.roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);

        user.setRoles(roles);

        this.userRepository.save(user);
        return "User registered successfully.";
    }
}
