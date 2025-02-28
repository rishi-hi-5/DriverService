package com.reftech.backend.driverbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class DriverBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverBackendApplication.class, args);
    }

}