package com.gk.jaikisaan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JaikisaanApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(JaikisaanApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Jai Kisaan application started");
        SpringApplication.run(JaikisaanApplication.class, args);
    }

}
