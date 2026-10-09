package com.apache.camel.poc.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserIdProcessor implements Processor {

    private final JdbcTemplate jdbcTemplate;

    public UserIdProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(Exchange exchange) {

        String userId = exchange.getMessage()
                .getBody(String.class)
                .trim();

        String sql = """
                INSERT INTO users(user_id)
                VALUES (?)
                """;

        jdbcTemplate.update(sql, Long.parseLong(userId));
    }
}