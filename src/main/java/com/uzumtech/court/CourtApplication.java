package com.uzumtech.court;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CourtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourtApplication.class, args);
    }

}
