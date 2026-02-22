package com.petwellnessplus.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.petwellnessplus.model.Role;
import com.petwellnessplus.repository.RoleRepository;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepo) {
    	logger.info("It's logger info test from datainitializer class");
    	logger.warn("It's logger warn test from datainitializer class");
        return args -> {
            if (roleRepo.count() == 0) {
                roleRepo.save(new Role(null, "ADMIN"));
                roleRepo.save(new Role(null, "DOCTOR"));
                roleRepo.save(new Role(null, "USER"));
            }
        };
    }
}
