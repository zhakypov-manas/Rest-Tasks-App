package com.example.laba.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskDao {
    private final JdbcTemplate jdbcTemplate;
}
