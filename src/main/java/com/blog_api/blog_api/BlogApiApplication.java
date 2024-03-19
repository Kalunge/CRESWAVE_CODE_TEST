package com.blog_api.blog_api;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@PropertySource("file:${user.dir}/.env")
@Configuration
@EnableSwagger2
public class BlogApiApplication {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(BlogApiApplication.class, args);
    }


}
