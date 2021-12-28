package com.example.electrolux_test_moskalenko;

import com.example.electrolux_test_moskalenko.model.machine.ActiveStatus;
import com.example.electrolux_test_moskalenko.model.machine.WashingMachine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Qualifier("machine")
    public WashingMachine newWashingMachine() {
        return new WashingMachine(null, ActiveStatus.OFF, "Electrolux EW6S4R 06 W");
    }

}
