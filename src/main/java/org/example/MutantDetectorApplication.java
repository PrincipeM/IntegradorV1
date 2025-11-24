package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Mutant Detector API.
 * 
 * This Spring Boot application provides REST endpoints to:
 * - Detect if a DNA sequence belongs to a mutant (POST /mutant)
 * - Retrieve statistics about DNA verifications (GET /stats)
 * 
 * @author MercadoLibre Challenge
 * @version 1.0.0
 */
@SpringBootApplication
public class MutantDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);
    }
}
