package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home Controller for root path redirection.
 * 
 * Redirects the root path "/" to the Swagger UI documentation page.
 */
@Controller
public class HomeController {

    /**
     * Redirects root path to Swagger UI.
     * 
     * @return redirect to Swagger UI
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}
