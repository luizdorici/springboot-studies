package org.example;

import org.example.domain.*;
import org.example.domain.enums.ClientType;
import org.example.domain.enums.PaymentState;
import org.example.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
class TesteApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TesteApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
