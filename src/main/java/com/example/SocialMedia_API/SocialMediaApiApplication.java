package com.example.SocialMedia_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.SocialMedia_API.dao.repository.postgres")
@EnableMongoRepositories(basePackages = "com.example.SocialMedia_API.dao.repository.mongo")
public class SocialMediaApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
    }
}
