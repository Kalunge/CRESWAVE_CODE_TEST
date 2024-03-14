package com.blog_api.blog_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BlogApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

}
//docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=blog_db_password -e MYSQL_DATABASE=blog_db -p 3306:3306 mysql:latest