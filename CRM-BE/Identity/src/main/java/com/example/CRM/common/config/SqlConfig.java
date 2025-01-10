package com.example.CRM.common.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class SqlConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    // Sử dụng @PostConstruct để thực thi khi ứng dụng khởi động
    @PostConstruct
    public void init() {
        try {
            // Đọc file SQL và thực thi
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("sql/create_procedures.sql")),
                    StandardCharsets.UTF_8
            ));

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            // Chạy câu lệnh SQL
            jdbcTemplate.execute(sql.toString());

        } catch (Exception e) {
            throw new RuntimeException(" Failed load SQL " + e.getMessage());
        }
    }
}
