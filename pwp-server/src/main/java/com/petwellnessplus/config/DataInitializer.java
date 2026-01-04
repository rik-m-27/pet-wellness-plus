package com.petwellnessplus.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.petwellnessplus.model.Role;
import com.petwellnessplus.repository.RoleRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepo) {
        return args -> {
            if (roleRepo.count() == 0) {
                roleRepo.save(new Role(null, "ADMIN"));
                roleRepo.save(new Role(null, "DOCTOR"));
                roleRepo.save(new Role(null, "USER"));
            }
        };
    }
}
