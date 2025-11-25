package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Home Controller
 * Endpoint raíz para verificar que el servicio está activo
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "service", "Mutant Detector API",
            "version", "1.0",
            "status", "running",
            "endpoints", "POST /mutant/, GET /stats"
        );
    }
}
