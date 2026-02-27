package com.example.sample_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot application entry point for Loan A Waala.
 * This application is a loan management system that allows users to create,
 * track, and manage loans with repayment schedules and notifications.
 */
@SpringBootApplication
@EnableScheduling  // Enables scheduled tasks like the NotificationScheduler
public class LoanAWaalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanAWaalaApplication.class, args);
    }
}
