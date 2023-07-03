package com.blog;

import com.blog.entities.Role;
import com.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogRestApiApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogRestApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] ROLES = new String[]{"ROLE_ADMIN", "ROLE_USER"};

        for (String roleName : ROLES) {
            Role role = new Role();
            role.setName(roleName);
            this.roleRepository.save(role);
        }

    }
}
