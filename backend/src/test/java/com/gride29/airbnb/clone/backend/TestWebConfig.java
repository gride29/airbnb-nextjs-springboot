package com.gride29.airbnb.clone.backend;

import com.gride29.airbnb.clone.backend.repository.RoleRepository;
import com.gride29.airbnb.clone.backend.repository.UserRepository;
import com.gride29.airbnb.clone.backend.security.jwt.JwtUtils;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootConfiguration
@AutoConfigurationPackage
@ComponentScan("com.gride29.airbnb.clone.backend.controllers")
@ComponentScan("com.gride29.airbnb.clone.backend.security.services")
@ComponentScan("com.gride29.airbnb.clone.backend.repository")
@EnableMongoRepositories("com.gride29.airbnb.clone.backend.repository")
public class TestWebConfig {
    @Bean
    public MongoOperations mongoOperations() {
        return new MongoTemplate(new SimpleMongoClientDbFactory("mongodb://localhost:27017/testdb"));
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDbFactory("mongodb://localhost:27017/testdb"));
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Mockito.mock(PasswordEncoder.class);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return Mockito.mock(JwtUtils.class);
    }

//    @Bean
//    public UserRepository userRepository() {
//        return Mockito.mock(UserRepository.class);
//    }

//    @Bean
//    public RoleRepository roleRepository() {
//        return Mockito.mock(RoleRepository.class);
//    }
}

