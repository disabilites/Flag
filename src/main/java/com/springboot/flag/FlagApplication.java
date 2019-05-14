package com.springboot.flag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class FlagApplication{

    public static void main(String[] args) {
        SpringApplication.run(FlagApplication.class, args);
    }

}
