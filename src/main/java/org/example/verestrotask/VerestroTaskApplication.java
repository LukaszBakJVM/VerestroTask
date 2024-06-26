package org.example.verestrotask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VerestroTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerestroTaskApplication.class, args);
    }

}
