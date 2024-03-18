package com.blog_api.blog_api;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@PropertySource("file:${user.dir}/.env")
public class BlogApiApplication {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(BlogApiApplication.class, args);
    }


}
