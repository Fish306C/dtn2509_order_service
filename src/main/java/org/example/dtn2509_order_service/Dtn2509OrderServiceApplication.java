package org.example.dtn2509_order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Dtn2509OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dtn2509OrderServiceApplication.class, args);
    }

}
