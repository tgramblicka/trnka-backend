package com.trnka.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.trnka.backend")
@Slf4j
@EnableJpaRepositories("com.trnka.backend.repository")
@EntityScan(basePackages = {"com.trnka.trnkadevice.domain" })

public class Application {

    public static void main(String[] args) {
        log.info("Starting trnka-backend");
        log.info("");

        SpringApplication.run(Application.class, args);

        log.info("");
        log.info("Trnka-backend is ready");
    }


}
