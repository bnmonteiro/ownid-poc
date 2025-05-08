package com.example.ownid;

import com.example.ownid.model.User;
import com.example.ownid.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createDefaultUser(UserRepository userRepository) {
        return args -> {

            List<String> defaultLoginIds = List.of(
                    "test@example.com",
                    "lnaranjo@princesscruises.com",
                    "bmonteiro@princesscruises.com",
                    "akarev@princesscruises.com",
                    "avyas@princesscruises.com",
                    "anton@ownid.com",
                    "Abhi.g1002"
            );

            defaultLoginIds.forEach(id -> {
                // Check if user already exists
                if (userRepository.findByLoginId(id).isEmpty()) {
                    User user = new User();
                    user.setLoginId(id);
                    userRepository.save(user);
                    System.out.println("Default user created with loginId: " + id);
                } else {
                    System.out.println("Default user already exists with loginId: " + id);
                }
            });

        };
    }
}
