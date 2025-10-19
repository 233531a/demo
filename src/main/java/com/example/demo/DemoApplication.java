package com.example.demo;

import com.example.demo.model.Project;
import com.example.demo.repository.Repository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("🚀 Приложение запущено! Перейди по адресу http://localhost:8080");
    }
}
