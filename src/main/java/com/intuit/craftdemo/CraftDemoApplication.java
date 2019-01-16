package com.intuit.craftdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.intuit.craftdemo")
public class CraftDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CraftDemoApplication.class, args);
    }

}

