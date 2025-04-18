package com.example.ownid;

import com.example.ownid.model.User;
import com.example.ownid.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createDefaultUser(UserRepository userRepository) {
        return args -> {
            String defaultLoginId = "test@example.com";

            // Check if user already exists
            if (userRepository.findByLoginId(defaultLoginId).isEmpty()) {
                User user = new User();
                user.setLoginId(defaultLoginId);
                userRepository.save(user);
                System.out.println("Default user created with loginId: " + defaultLoginId);
            } else {
                System.out.println("Default user already exists.");
            }
        };
    }
}
