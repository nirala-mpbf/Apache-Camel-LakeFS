package com.apache.camel.poc.camelProcessor;

import com.apache.camel.poc.dao.UserRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class UserIdProcessor implements Processor {

    private final UserRepository userRepository;

    public UserIdProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void process(Exchange exchange) {
        String userIdText = exchange.getMessage().getBody(String.class);

        if (userIdText == null || userIdText.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }

        userIdText = userIdText.trim();

        long userId;

        try {
            userId = Long.parseLong(userIdText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("User ID must be a valid number: " + userIdText);
        }

        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero: " + userId);
        }

        userRepository.save(userId);
    }
}