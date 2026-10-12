package com.apache.camel.poc.dao.impl;

import com.apache.camel.poc.dao.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(long userId) {
        String sql = """
                INSERT INTO users(user_id)
                VALUES (?)
                ON CONFLICT (user_id) DO NOTHING
            """;

        jdbcTemplate.update(sql, userId);
    }
}
