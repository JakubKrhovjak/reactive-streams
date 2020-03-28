package com.example.reactiveproducer;

import com.example.reactiveproducer.entity.User;
import com.example.reactiveproducer.repository.UserRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ReactiveProducerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveProducerApplication.class)
            .web(WebApplicationType.REACTIVE)
            .run();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepository.deleteAll().subscribe();
        User user = new User().setUsername("test").setPassword(passwordEncoder.encode("123"));
        userRepository.save(user).subscribe();
    }

    @Bean
    public LoggingEventListener mongoEventListener() {
        return new LoggingEventListener();
    };

  }
