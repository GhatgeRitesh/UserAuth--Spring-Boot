package com.ritesh.UserAuth.Configuration;

import com.ritesh.UserAuth.Model.User;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AppConfig {

    @Bean
    public User user() {
        User user = new User();
        // Set default values or perform any necessary initialization
        return user;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/userauth_test")
                .username("root")
                .password("Rit!@#1115")
                .build();
        return dataSource;
    }
}
