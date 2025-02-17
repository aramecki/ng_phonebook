package com.example.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DbStuff {

    @Autowired
    private DataSource dataSource;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {

                String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "firstName VARCHAR(255) NOT NULL, " +
                        "lastName VARCHAR(255), " +
                        "firstNumber VARCHAR(255), " +
                        "secondNumber VARCHAR(255), " +
                        "firstAddress VARCHAR(255), " +
                        "secondAddress VARCHAR(255), " +
                        "email VARCHAR(255), " +
                        "active BOOL NOT NULL)";
                statement.execute(sql);

                System.out.println("Tab contacts created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}