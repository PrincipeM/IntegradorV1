package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for the application.
 * 
 * This configuration:
 * - Disables CSRF protection for API endpoints
 * - Allows frame options for H2 Console UI
 * - Permits all requests to all endpoints
 */
@Configuration
@EnableWebSecurity
public class H2ConsoleConfig {

    /**
     * Configures security to allow public access to all endpoints.
     * 
     * @param http HttpSecurity configuration
     * @return SecurityFilterChain with public access enabled
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );
        
        return http.build();
    }
}
