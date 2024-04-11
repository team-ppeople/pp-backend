package com.pp.api;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

import static java.util.TimeZone.getTimeZone;

@SpringBootApplication
public class WebAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAPIApplication.class, args);
    }

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(getTimeZone("Asia/Seoul"));
    }

}
