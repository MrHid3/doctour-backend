package com.doctour.doctourbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DoctourBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctourBeApplication.class, args);
    }

}
