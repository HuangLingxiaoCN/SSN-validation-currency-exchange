package com.example.springboottest20220608;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SSNValidationCurrencyExchange {

    public static void main(String[] args) {
        SpringApplication.run(SSNValidationCurrencyExchange.class, args);
    }

}
