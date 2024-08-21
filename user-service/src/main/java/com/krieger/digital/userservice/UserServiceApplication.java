package com.krieger.digital.userservice;

import com.krieger.digital.userservice.enums.Active;
import com.krieger.digital.userservice.enums.Role;
import com.krieger.digital.userservice.model.User;
import com.krieger.digital.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    public UserServiceApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        final String pass = "$2a$10$Pv0dmYUltssPfBMRlvqLierOlebiEIiFSFHihe0l9tIa87SoDB1LS";
        var admin = User.builder()
                .username("admin")
                .email("yaldaghasemi@gmail.com")
                .password(pass)
                .active(Active.ACTIVE)
                .role(Role.ADMIN).build();

        if (userRepository.findByUsername("admin").isEmpty()) userRepository.save(admin);
    }
}
